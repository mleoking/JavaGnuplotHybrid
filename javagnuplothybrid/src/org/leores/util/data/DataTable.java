package org.leores.util.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.leores.ecpt.TRuntimeException;
import org.leores.util.ObjArray;
import org.leores.util.ObjArray.ObjArrayComparator;
import org.leores.util.U;
import org.leores.util.able.Processable2;

public class DataTable implements Cloneable {
	public String info;
	protected String[] colNames;
	protected List<Object[]> rows;
	private int nColumns;

	public DataTable(String info, int nColumns) {
		this.info = info;
		this.nColumns = nColumns;
		rows = new ArrayList<Object[]>();
	}

	public String[] getColNames() {
		return colNames;
	}

	public boolean setColNames(String[] colNames) {
		boolean rtn = false;
		if (colNames != null && colNames.length == nColumns) {
			rtn = true;
			this.colNames = colNames;
		}
		return rtn;
	}

	public <R, B> boolean addAll(boolean bAddUnEqualSizedList, Processable2<R, Integer, B> pa2Element, List<B>... lists) {
		boolean rtn = false;
		List[] tLists = lists;
		if (tLists != null && tLists.length == nColumns) {
			rtn = true;
			if (pa2Element != null) {
				for (int i = 0; i < nColumns; i++) {
					tLists[i] = U.processList(lists[i], pa2Element);
				}
			}
			int size = tLists[0].size();
			if (!U.bEqualSize(tLists)) {
				if (bAddUnEqualSizedList) {
					size = U.minSize(tLists);
				} else {
					rtn = false;
					throw new TRuntimeException("addAll: lists are not of the same size for DataTable: " + info);
				}
			}
			for (int i = 0; i < size; i++) {
				Object[] row = new Object[nColumns];
				for (int j = 0; j < nColumns; j++) {
					row[j] = tLists[j].get(i);
				}
				add(row);
			}
		}
		return rtn;
	}

	public <R> boolean addAll(boolean bAddUnEqualSizedList, Processable2<R, Integer, Double> pa2Element, double[]... arrays) {
		boolean rtn = false;
		if (arrays != null && arrays.length == nColumns) {
			List<Double>[] lists = new List[nColumns];
			for (int i = 0; i < arrays.length; i++) {
				lists[i] = U.parseList(arrays[i]);
			}
			rtn = addAll(bAddUnEqualSizedList, pa2Element, lists);
		}
		return rtn;
	}

	public <R, B> boolean addAll(boolean bAddUnEqualSizedList, Processable2<R, Integer, B[]> pa2Element, List<B[]> list) {
		boolean rtn = false;
		if (list != null && list.size() > 0) {
			B[] b0 = list.get(0);
			if (b0 != null && b0.length == nColumns) {
				List tList = list;
				if (pa2Element != null) {
					tList = U.processList(list, pa2Element);
				}
				rtn = rows.addAll(tList);
			}
		}
		return rtn;
	}
	
	public boolean insert(int index, Object... objs) {
		boolean rtn = false;
		if (objs != null && objs.length == nColumns) {
			rtn = true;
			rows.add(index, objs);
		}
		return rtn;
	}

	public boolean add(Object... objs) {
		boolean rtn = false;
		if (objs != null && objs.length == nColumns) {
			rtn = true;
			rows.add(objs);
		}
		return rtn;
	}

	public int nColumns() {
		return nColumns;
	}

	public int nRows() {
		return rows.size();
	}

	public Object[] getRow(int i) {
		return rows.get(i);
	}

	public Object[] setRow(int i, Object[] row) {
		return rows.set(i, row);
	}

	public Object[] removeRow(int i) {
		return rows.remove(i);
	}

	public List<Object[]> getRows() {
		return rows;
	}

	public List<Object> getColumn(int i) {
		List<Object> rtn = null;
		if (i >= 0 && i < nColumns) {
			rtn = new ArrayList<Object>();
			for (int j = 0, mj = rows.size(); j < mj; j++) {
				Object[] row = rows.get(j);
				rtn.add(row[i]);
			}
		}
		return rtn;
	}

	/**
	 * Can be used t crate a sub DataTable with rows from iRowFrom to iRowTo and
	 * columns with iCols. There could be repeated ids in iCols and iCols.length
	 * could be bigger than nColumns so as to generate a DataTable with some
	 * repeated columns when necessary.
	 * 
	 * @param iRowFrom
	 * @param iRowTo
	 * @param iCols
	 * @return
	 */
	public DataTable subTable(int iRowFrom, int iRowTo, int... iCols) {
		DataTable rtn = null;
		if (iRowFrom >= 0 && iRowTo >= iRowFrom && iRowTo < rows.size()) {
			int[] iColsTU = iCols;
			if (iColsTU == null) {//null means all columns in the original order.
				iColsTU = new int[nColumns];
				for (int i = 0; i < iColsTU.length; i++) {
					iColsTU[i] = i;
				}
			}
			rtn = new DataTable(info, iColsTU.length);
			if (colNames != null) {
				String[] subColNames = new String[iColsTU.length];
				for (int i = 0; i < iColsTU.length; i++) {
					subColNames[i] = colNames[iColsTU[i]];
				}
				rtn.setColNames(subColNames);
			}
			for (int i = iRowFrom; i <= iRowTo; i++) {
				Object[] row = rows.get(i);
				Object[] subRow = new Object[iColsTU.length];
				for (int j = 0; j < iColsTU.length; j++) {
					subRow[j] = row[iColsTU[j]];
				}
				rtn.add(subRow);
			}
		}
		return rtn;
	}

	public DataTable subRows(int iRowFrom, int iRowTo) {
		return subTable(iRowFrom, iRowTo, null);
	}

	public DataTable subColumns(int... iCols) {
		return subTable(0, rows.size() - 1, iCols);
	}

	@SuppressWarnings("unchecked")
	public <B> int processRows(Processable2<B[], Integer, B[]> pa2Row) {
		int rtn = -1;
		if (pa2Row != null) {
			int nRows = rows.size();
			Processable2 pa2 = pa2Row;
			rows = U.processList(rows, pa2);
			rtn = nRows - rows.size();
		}
		return rtn;
	}

	public void sort(Comparator<? super Object[]> c) {
		Collections.sort(rows, c);
	}

	public void sort(boolean bAscending) {
		sort(new ObjArrayComparator<Object>(bAscending));
	}

	public void sort() {
		sort(true);
	}

	public DataTable clone() {
		return subTable(0, nRows() - 1, null);
	}
}
