package org.leores.plot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.leores.ecpt.TRuntimeException;
import org.leores.util.Logger;
import org.leores.util.SysUtil;
import org.leores.util.SysUtil.Command;
import org.leores.util.U;
import org.leores.util.able.Processable2;
import org.leores.util.data.DataTable;
import org.leores.util.data.DataTableSet;

/**
 * We do not check the null of parameters intentionally to make the program stop
 * as soon as error occurs.
 * 
 * @author leoking
 * 
 */
public class JGnuplot extends Logger {
	public Integer id;
	public String style, style2d, style3d, header;
	public String plot2d, plot2dBar, plot3d, plotDensity, plotImage, multiplot, plotx;
	public String beforeStyle, beforeStyleVar, afterStyleVar, afterStyle, beforeHeader, extra, afterHeader, bp;
	public String terminal, output;
	public String sPatNumOut;
	protected String sFTemp;
	public String sFLoad;
	protected static JGnuplot tJGnuplot;
	protected static Integer maxId = 0;

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

	public synchronized static int nextId() {
		maxId++;
		return maxId;
	}

	public boolean initialize(boolean bCopyLoadFromFile) {
		boolean rtn = false;
		id = nextId();
		bp = "";
		sFLoad = "jgnuplot.xml";
		String sFTempEnd = "_" + id;
		sFTemp = "JGnuplotTemp" + sFTempEnd + ".plt";
		if (bCopyLoadFromFile) {
			rtn = copyLoadFromFile();
		} else {
			JGnuplot jg = tJGnuplot();
			style = jg.style;
			style2d = jg.style2d;
			style3d = jg.style3d;
			header = jg.header;
			extra = jg.extra;
			plot2d = jg.plot2d;
			plot2dBar = jg.plot2dBar;
			plot3d = jg.plot3d;
			plotDensity = jg.plotDensity;
			plotImage = jg.plotImage;
			multiplot = jg.multiplot;
			plotx = jg.plotx;
			sPatNumOut = jg.sPatNumOut;
			rtn = true;
		}
		initialize();
		return rtn;
	}

	public void initialize() {
	}

	public boolean load(String str) {
		return U.loadFromString(this, str);
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

		if (plot != null && sFile != null && prep()) {
			String sFileTU = U.toValidFileName(sFile);
			String codeEvaled = code;
			if (codeEvaled == null) {
				codeEvaled = plot2d;
				int nColumn = plot.nColumn(0, 0);
				if (nColumn == 3) {
					codeEvaled = plot3d;
				}
			}
			for (int i = 0; i < 5; i++) {
				codeEvaled = U.eval(codeEvaled, this, U.EVAL_InvalidIgnore | U.EVAL_NullIgnore);
			}
			rtn = plot.compile(codeEvaled, sFileTU, sPatNumOut);
		}

		return rtn;
	}

	public boolean compile(Plot plot, String code) {
		return compile(plot, code, plot.info + ".plt");
	}

	public boolean compile(Plot plot) {
		return compile(plot, null, plot.info + ".plt");
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
		U.deleteFile(sFTemp);
		if (compile(plot, code, sFTemp)) {
			String sCmd = sCommand(sFTemp, (flags & JG_Pause) > 0);
			rtn = SysUtil.execCmd(sCmd, (flags & JG_Log) > 0, (flags & JG_InNewThread) > 0);
			if ((flags & JG_DeleteTempFile) > 0) {
				U.deleleFiles("^" + sFTemp + ".*");
			}
		}
		return rtn;
	}

	public Command execute(Plot plot, String code) {
		return execute(plot, code, JG_Default);
	}

	public Command execute(Plot plot) {
		return execute(plot, null, JG_Default);
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

	public Command executeA(Plot plot) {
		return execute(plot, null, JG_All);
	}

	public static class Plot extends Logger {
		public String info;
		public String beforeStyle2, beforeStyleVar2, afterStyleVar2, afterStyle2, beforeHeader2, extra2, extra3, afterHeader2, bp2;
		public String xlabel, ylabel, zlabel, xrange, yrange, zrange;
		protected List<DataTableSet> lDataTableSet;
		protected String sPatNumOut;
		protected String sFile;
		protected Integer fid;

		protected Integer nextFid() {
			fid++;
			return fid;
		}

		public Plot(String info) {
			this.info = info;
			lDataTableSet = new ArrayList<DataTableSet>();
			initialize();
		}

		public void initialize() {
			fid = 0;
			bp2 = "";
		}

		public boolean autoSetLabels() {
			boolean rtn = false;
			DataTableSet dts = getDataTableSet(0);
			if (dts != null) {
				int ndt = dts.size();
				if (ndt > 0) {
					DataTable dt = dts.get(0);
					String[] cns = dt.getColNames();
					if (cns != null) {
						rtn = true;
						if (ndt == 1) {
							if (cns.length > 0) {//1 or more
								xlabel = cns[0];
							}
							if (cns.length > 1) {//2 or more
								ylabel = cns[1];
							}
							if (cns.length > 2) {//3 or more
								zlabel = cns[2];
							}
						} else {//ndt>1
							if (cns.length > 1) {//2 or more
								xlabel = cns[0];
							}
							if (cns.length > 2) {//3 or more
								ylabel = cns[1];
							}
						}

					}
				}
			}
			return rtn;
		}

		public boolean load(String str) {
			return U.loadFromString(this, str);
		}

		public DataTableSet addNewDataTableSet(String info) {
			DataTableSet dataTableSet = new DataTableSet(info);
			add(dataTableSet);
			return dataTableSet;
		}

		public int add(DataTableSet... dataTableSets) {
			int rtn = 0;
			for (int i = 0; i < dataTableSets.length; i++) {
				if (dataTableSets[i] != null) {
					if (lDataTableSet.add(dataTableSets[i])) {
						rtn++;
					}
				}
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

		public DataTableSet getDataTableSet(int i) {
			DataTableSet rtn = null;
			if (i >= 0 && i < lDataTableSet.size()) {
				return lDataTableSet.get(i);
			}
			return rtn;
		}

		public DataTable getDataTable(int iDataTableSet, int iDataTable) {
			DataTable rtn = null;
			DataTableSet dts = getDataTableSet(iDataTableSet);
			if (dts != null) {
				rtn = dts.get(iDataTable);
			}
			return rtn;
		}

		public List<DataTableSet> getDataTableSets() {
			return lDataTableSet;
		}

		protected boolean prep() {
			return true;
		}

		public boolean compile(String code, String sFile, String sPatNumOut) {
			boolean rtn = false;
			if (code != null && prep()) {
				this.sFile = sFile;
				this.sPatNumOut = sPatNumOut;
				//U.deleteFile(sFile);
				Scanner scanner = new Scanner(code);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					try {
						//Those lines with invalid/null fields/methods/expressions will be cause a runtime exception here.
						String lineEvaled = line;
						for (int i = 0; i < 5 && (!U.bNoEval(lineEvaled)); i++) {
							lineEvaled = U.eval(lineEvaled, this, U.EVAL_InvalidException | U.EVAL_NullException);
						}
						if (U.bNoEval(lineEvaled)) {
							lineEvaled = lineEvaled.substring(U.sNoEval.length());
						}
						if (lineEvaled.length() > 0) {
							U.appendFile(sFile, lineEvaled + "\n");
						}
						rtn = true;
					} catch (TRuntimeException exception) {
						//log(exception);
					}
				}
				scanner.close();
			}
			return rtn;
		}

		public int size() {
			return lDataTableSet.size();
		}

		public int nColumn(int iDataTableSet, int iDataTable) {
			int rtn = -1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size()) {
				DataTableSet dts = lDataTableSet.get(iDataTableSet);
				if (iDataTable >= 0 && iDataTable < dts.size()) {
					rtn = dts.get(iDataTable).nColumns();
				}
			}
			return rtn;
		}

		public Integer size(String dataTableSetNum) {
			Integer rtn = null;
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size()) {
				rtn = lDataTableSet.get(iDataTableSet).size();
			}
			return rtn;
		}

		public Integer nColumn(String dataTableSetNum, String dataTableNum) {
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			int iDataTable = Integer.parseInt(dataTableNum) - 1;
			Integer rtn = nColumn(iDataTableSet, iDataTable);
			if (rtn < 0) {
				rtn = null;
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
				sBuffer.append("info" + (i + 1) + "=\"" + dts.info + "\";\n");
				for (int j = 0, mj = dts.size(); j < mj; j++) {
					DataTable dt = dts.get(j);
					sBuffer.append("info" + (i + 1) + "_" + (j + 1) + "=\"" + dt.info + "\";\n");
				}
			}
			String sInfos = sBuffer.toString();
			sInfos = U.sNoEval + sInfos.substring(0, sInfos.length() - 1);//remove the last line break;
			return sInfos;
		}

		protected void saveToFile(String sFile, DataTable dt, String type) {
			String sLastColumn1 = null;
			String[] colNames = dt.getColNames();
			try {
				FileWriter fw = new FileWriter(U.createIfNotExist(sFile), true);
				if (colNames != null) {
					String line = "#" + U.wrap(colNames, "", "", " ");
					fw.append(line + "\n");
				}
				for (int i = 0, mj = dt.nRows(); i < mj; i++) {
					Object[] row = dt.getRow(i);
					String[] sRow = U.toStrArray(Arrays.asList(row), sPatNumOut);
					if ("3d".equals(type)) {
						if (sLastColumn1 == null) {
							sLastColumn1 = sRow[0];
						}
						if (!sLastColumn1.equals(sRow[0])) {
							fw.append("\n");
							sLastColumn1 = sRow[0];
						}
					}
					String line = U.wrap(sRow, "", "", " ");
					fw.append(line + "\n");
				}
				fw.append("e\n");
				fw.flush();
				fw.close();
			} catch (IOException e) {
				log(e);
			}
		}

		public String data(String dataTableSetNum, String dataTableNum, String type) {
			String rtn = null;
			if (data2f(sFile, dataTableSetNum, dataTableNum, type) != null) {
				rtn = "";
			}
			return rtn;
		}

		public String data2f(String fn, String dataTableSetNum, String dataTableNum, String type) {
			String rtn = null;
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (fn != null && iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size() && type != null) {
				DataTableSet dts = lDataTableSet.get(iDataTableSet);
				if (dataTableNum == null) {
					for (int i = 0, mi = dts.size(); i < mi; i++) {
						DataTable dt = dts.get(i);
						saveToFile(fn, dt, type);
					}
					rtn = fn;
				} else {
					int iDataTable = Integer.parseInt(dataTableNum) - 1;
					if (iDataTable >= 0 && iDataTable < dts.size()) {
						DataTable dt = dts.get(iDataTable);
						saveToFile(fn, dt, type);
						rtn = fn;
					}
				}
			}
			return rtn;
		}

		public String data2f(String dataTableSetNum, String dataTableNum, String type) {
			String rtn = data2f(sFile + "-D" + nextFid(), dataTableSetNum, dataTableNum, type);
			return rtn;
		}

		public String data2f(String dataTableSetNum, String type) {
			String rtn = data2f(dataTableSetNum, null, type);
			return rtn;
		}

		public String data(String dataTableSetNum, String type) {
			return data(dataTableSetNum, null, type);
		}

		public String data(String dataTableSetNum) {
			return data(dataTableSetNum, null, "2d");
		}

		public String setInfo(String dataTableSetNum, String info) {
			int iDataTableSet = Integer.parseInt(dataTableSetNum) - 1;
			if (iDataTableSet >= 0 && iDataTableSet < lDataTableSet.size()) {
				DataTableSet dts = lDataTableSet.get(iDataTableSet);
				dts.info = info;
			}
			return "";
		}
	}

}
