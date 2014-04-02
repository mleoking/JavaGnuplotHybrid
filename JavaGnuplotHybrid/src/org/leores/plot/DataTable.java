package org.leores.plot;

import java.util.ArrayList;
import java.util.List;

import org.leores.ecpt.TRuntimeException;
import org.leores.util.U;

public class DataTable {
	public String info;
	protected List<Object[]> rows;
	private int nColumn;

	public DataTable(String info, int nColumn) {
		this.info = info;
		this.nColumn = nColumn;
		rows = new ArrayList<Object[]>();
	}

	public boolean addAll(boolean bAddUnEqualSizedList, List... lists) {
		boolean rtn = false;
		if (lists != null && lists.length == nColumn) {
			rtn = true;
			int size = lists[0].size();
			if (!U.bEqualSize(lists)) {
				if (bAddUnEqualSizedList) {
					size = U.minSize(lists);
				} else {
					rtn = false;
					throw new TRuntimeException("addAll: lists are not of the same size for DataTable: " + info);
				}
			}
			for (int i = 0; i < size; i++) {
				Object[] row = new Object[nColumn];
				for (int j = 0; j < nColumn; j++) {
					row[j] = lists[j].get(i);
				}
				add(row);
			}
		}
		return rtn;
	}

	public boolean addAll(boolean bAddUnEqualSizedList, double[]... arrays) {
		boolean rtn = false;
		if (arrays != null && arrays.length == nColumn) {
			List[] lists = new List[nColumn];
			for (int i = 0; i < arrays.length; i++) {
				lists[i] = U.parseList(arrays[i]);
			}
			rtn = addAll(bAddUnEqualSizedList, lists);
		}
		return rtn;
	}

	public boolean add(Object... objs) {
		boolean rtn = false;
		if (objs != null && objs.length == nColumn) {
			rtn = true;
			rows.add(objs);
		}
		return rtn;
	}

	public int nColumn() {
		return nColumn;
	}

	public int nRow() {
		return rows.size();
	}

	public Object[] getRow(int i) {
		return rows.get(i);
	}

	public Object[] removeRow(int i) {
		return rows.remove(i);
	}
}
