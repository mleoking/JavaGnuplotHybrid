package org.leores.plot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.leores.ecpt.TRuntimeException;
import org.leores.math.Randomer;
import org.leores.util.DataTable;
import org.leores.util.DataTableSet;
import org.leores.util.Logger;
import org.leores.util.SysUtil;
import org.leores.util.SysUtil.Command;
import org.leores.util.U;
import org.leores.util.able.Processable2;

/**
 /**
 * We do not check the null of parameters intentionally to make the program stop
 * as soon as error occurs.
 * 
 * @author leoking
 * 
 */
public class JGnuplot extends Logger {
	public String style2d, style3d, header, extra;
	public String beforeStyle, beforeStyleVar, afterStyleVar, afterStyle, beforeHeader, afterHeader, beforePlot;
	public String plot2d, plot2dx, plot3d, multiplot, plotx;
	public String numOutPattern;
	protected String sFTemp;
	public String sFLoad;
	protected static JGnuplot tJGnuplot;

	public static final int JG_InNewThread = 0x00000001;
	public static final int JG_DeleteTempFile = 0x00000002;
	public static final int JG_Pause = 0x00000004;
	public static final int JG_Log = 0x00000008;
	public static final int JG_Default = JG_DeleteTempFile | JG_Pause | JG_Log;
	public static final int JG_All = ~0x00000000;

	public JGnuplot(boolean bCopyLoadFromFile) {
		initialize(bCopyLoadFromFile);
	}

	public JGnuplot() {
		initialize(false);
	}

	public static synchronized JGnuplot tJGnuplot() {
		if (tJGnuplot == null) {
			tJGnuplot = new JGnuplot(true);
		}
		return tJGnuplot;
	}

	public static int delAllTempFiles() {
		return U.deleleFiles("JGnuplotTemp.*plt");
	}

	public boolean initialize(boolean bCopyLoadFromFile) {
		boolean rtn = false;
		beforePlot = "";
		sFLoad = "jgnuplot.xml";
		Date startDate = new Date();
		Randomer rnd = new Randomer();
		String sFTempEnd = U.format(startDate, "_HHmm_") + Math.abs((rnd.nextInt() % 100000));
		sFTemp = "JGnuplotTemp" + sFTempEnd + ".plt";
		if (bCopyLoadFromFile) {
			rtn = copyLoadFromFile();
		} else {
			JGnuplot jg = tJGnuplot();
			header = jg.header;
			extra = jg.extra;
			style2d = jg.style2d;
			style3d = jg.style3d;
			plot2d = jg.plot2d;
			plot3d = jg.plot3d;
			multiplot = jg.multiplot;
			numOutPattern = jg.numOutPattern;
			rtn = true;
		}
		rtn = rtn && initialize();
		return rtn;
	}

	public boolean initialize() {
		return true;
	}

	public boolean copyLoadFromFile() {
		U.copyFileFromClassPath(JGnuplot.class, sFLoad, sFLoad, false);//this also works in a jar file
		return U.loadFromXML(this, sFLoad, false);
	}

	public void afterLoadObj(String sLoadMethod) {
		U.processFields(this, U.modAll, new Processable2.TrimStringField());
	}

	protected boolean prep() {
		return true;
	}

	public boolean compile(Plot plot, String code, String sFile) {
		boolean rtn = false;

		if (plot != null && code != null && sFile != null && prep()) {
			String codeEvaled = code;
			for (int i = 0; i < 3; i++) {
				codeEvaled = U.eval(codeEvaled, this, U.EVAL_InvalidIgnore | U.EVAL_NullIgnore);
			}
			rtn = plot.compile(codeEvaled, sFile, numOutPattern);
		}

		return rtn;
	}

	public boolean compile(Plot plot, String code) {
		return compile(plot, code, plot.info + ".plt");
	}

	protected String sCommand(String sFile, boolean bPause) {
		String rtn = "gnuplot " + sFile;
		if (bPause) {
			rtn += " -e \"pause mouse key\"";
		}
		return rtn;
	}

	/**
	 * flags:<br>
	 * <b>JG_InNewThread</b> <br>
	 * <b>JG_DeleteTempFile:</b> use ~JG_DeleteTempFile flag to keep the temp
	 * file. This is useful when doing multi-thread plotting. The temp files
	 * shold be deleted at the end all together (delAllTempFiles()) to guarantee
	 * all figures can be plotted before temp files are deleted.<br>
	 * <b>JG_Pause</b><br>
	 * <b>JG_Log</b><br>
	 * <b>JG_Default</b> = JG_DeleteTempFile | JG_Pause | JG_Log<br>
	 * <b>JG_All</b>
	 * 
	 * @param plot
	 * @param code
	 * @param flags
	 * @return
	 */
	public Command execute(Plot plot, String code, int flags) {
		Command rtn = null;
		if (compile(plot, code, sFTemp)) {
			String sCmd = sCommand(sFTemp, (flags & JG_Pause) > 0);
			rtn = SysUtil.execCmd(sCmd, (flags & JG_Log) > 0, (flags & JG_InNewThread) > 0);
			if ((flags & JG_DeleteTempFile) > 0) {
				U.deleteFile(sFTemp);
			}
		}
		return rtn;
	}

	public Command execute(Plot plot, String code) {
		return execute(plot, code, JG_Default);
	}

	/**
	 * Execute the plot in a new thread so that the current thread can go on
	 * without waiting. The execution results can be obtained through calling
	 * waitForRtn() of the return Command obj (this will cause the current
	 * thread to wait).
	 * 
	 * @param plot
	 * @param code
	 * @return
	 */
	public Command executeA(Plot plot, String code) {
		return execute(plot, code, JG_All);
	}

	public static class Plot {
		public String info;
		protected List<DataTableSet> lDataTableSet;
		protected String numOutPattern;
		protected String sFile;

		public Plot(String info) {
			this.info = info;
			lDataTableSet = new ArrayList<DataTableSet>();
		}

		public DataTableSet addNewDataTableSet(String info) {
			DataTableSet dataTableSet = new DataTableSet(info);
			add(dataTableSet);
			return dataTableSet;
		}

		public boolean add(DataTableSet dataTableSet) {
			boolean rtn = false;
			if (dataTableSet != null) {
				rtn = lDataTableSet.add(dataTableSet);
			}

			return rtn;
		}

		public boolean addAll(Collection<? extends DataTableSet> dataTableSets) {
			boolean rtn = false;
			if (dataTableSets != null) {
				rtn = lDataTableSet.addAll(dataTableSets);
			}
			return rtn;
		}

		public DataTableSet getDataTableSet(Integer i) {
			return lDataTableSet.get(i);
		}

		public List<DataTableSet> getDataTableSets() {
			return lDataTableSet;
		}

		protected boolean prep() {
			return true;
		}

		public boolean compile(String code, String sFile, String numOutPattern) {
			boolean rtn = false;
			if (code != null && prep()) {
				this.sFile = sFile;
				this.numOutPattern = numOutPattern;
				U.deleteFile(sFile);
				Scanner scanner = new Scanner(code);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					try {
						//Those lines with invalid/null fields/methods/expressions will be cause a runtime exception here.
						String lineEvaled = line;
						for (int i = 0; i < 3; i++) {
							lineEvaled = U.eval(lineEvaled, this, U.EVAL_InvalidException | U.EVAL_NullException);
						}
						if (lineEvaled.length() > 0) {
							U.appendToFile(sFile, lineEvaled + "\n");
						}
						rtn = true;
					} catch (TRuntimeException exception) {

					}
				}
				scanner.close();
			}
			return rtn;
		}

		public int size() {
			return lDataTableSet.size();
		}

		public Integer size(String dataTableSetNum) {
			Integer rtn = null;
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size()) {
				rtn = lDataTableSet.get(iDataTableSet).size();
			}
			return rtn;
		}

		public String info(String dataTableSetNum) {
			String rtn = null;
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size()) {
				DataTableSet dts = lDataTableSet.get(iDataTableSet);
				rtn = dts.info;
			}
			return rtn;
		}

		public String info(String dataTableSetNum, String dataTableNum) {
			String rtn = null;
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size()) {
				DataTableSet dts = lDataTableSet.get(iDataTableSet);
				int iDataTable = Integer.parseInt(dataTableNum) - 1;
				if (iDataTable >= 0 && iDataTable < dts.size()) {
					rtn = dts.get(iDataTable).info;
				}
			}
			return rtn;
		}

		public String infos() {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0, mi = lDataTableSet.size(); i < mi; i++) {
				DataTableSet dts = lDataTableSet.get(i);
				sBuffer.append("info" + (i + 1) + "=\"" + dts.info + "\"\n");
				for (int j = 0, mj = dts.size(); j < mj; j++) {
					DataTable dt = dts.get(j);
					sBuffer.append("info" + (i + 1) + "_" + (j + 1) + "=\"" + dt.info + "\"\n");
				}
			}
			String sInfos = sBuffer.toString();
			sInfos = sInfos.substring(0, sInfos.length() - 1);//remove the last line break;
			return sInfos;
		}

		protected void saveToFile(String sFile, DataTable dt, String type) {
			String sLastColumn1 = null;
			for (int i = 0, mj = dt.nRow(); i < mj; i++) {
				Object[] row = dt.getRow(i);
				String[] sRow = U.toStrArray(Arrays.asList(row), numOutPattern);
				if ("3d".equals(type)) {
					if (sLastColumn1 == null) {
						sLastColumn1 = sRow[0];
					}
					if (!sLastColumn1.equals(sRow[0])) {
						U.appendToFile(sFile, "\n");
						sLastColumn1 = sRow[0];
					}
				}
				String line = U.wrap(sRow, "", "", " ");
				U.appendToFile(sFile, line + "\n");

			}
			U.appendToFile(sFile, "e\n");
		}

		public String data(String dataTableSetNum, String dataTableNum, String type) {
			String rtn = null;
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size() && type != null) {
				DataTableSet dts = lDataTableSet.get(iDataTableSet);
				if (dataTableNum == null) {
					for (int i = 0, mi = dts.size(); i < mi; i++) {
						DataTable dt = dts.get(i);
						saveToFile(sFile, dt, type);
					}
					rtn = "";
				} else {
					int iDataTable = Integer.parseInt(dataTableNum) - 1;
					if (iDataTable >= 0 && iDataTable < dts.size()) {
						DataTable dt = dts.get(iDataTable);
						saveToFile(sFile, dt, type);
						rtn = "";
					}
				}
			}
			return rtn;
		}

		public String data(String dataTableSetNum, String type) {
			return data(dataTableSetNum, null, type);
		}

		public String data(String dataTableSetNum) {
			return data(dataTableSetNum, null, "2d");
		}
	}
}
