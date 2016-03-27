package org.leores.util.data;

import java.util.ArrayList;
import java.util.List;

import org.leores.util.able.Processable2;

public class DataTableSet implements Cloneable {
	public String info;
	public List<DataTable> members;

	public DataTableSet(String info) {
		this.info = info;
		members = new ArrayList<DataTable>();
	}

	public DataTableSet() {
		this(null);
	}

	public boolean add(DataTable dataTable) {
		boolean rtn = false;
		if (dataTable != null) {
			rtn = true;
			members.add(dataTable);
		}
		return rtn;
	}

	public boolean add(DataTable... dataTables) {
		boolean rtn = false;
		if (dataTables != null && dataTables.length > 0) {
			rtn = true;
			for (int i = 0; i < dataTables.length; i++) {
				if (!add(dataTables[i])) {
					rtn = false;
					break;
				}
			}
		}
		return rtn;
	}

	public DataTable addNewDataTable(String info, int nColumn) {
		DataTable rtn = new DataTable(info, nColumn);
		add(rtn);
		return rtn;
	}

	public <R, B> DataTable addNewDataTable(String info, boolean bAddUnEqualSizedList, Processable2<R, Integer, B> pa2Element, List<B>... lists) {
		DataTable rtn = new DataTable(info, lists.length);
		if (rtn.addAll(bAddUnEqualSizedList, pa2Element, lists)) {
			add(rtn);
		} else {
			rtn = null;
		}
		return rtn;
	}

	public <R> DataTable addNewDataTable(String info, boolean bAddUnEqualSizedList, Processable2<R, Integer, Double> pa2Element, double[]... arrays) {
		DataTable rtn = new DataTable(info, arrays.length);
		if (rtn.addAll(bAddUnEqualSizedList, pa2Element, arrays)) {
			add(rtn);
		} else {
			rtn = null;
		}
		return rtn;
	}

	public <R, B> DataTable addNewDataTable(String info, boolean bAddUnEqualSizedList, Processable2<R, Integer, B[]> pa2Element, List<B[]> list) {
		DataTable rtn = null;
		if (list != null && list.size() > 0) {
			B[] b0 = list.get(0);
			if (b0 != null) {
				rtn = new DataTable(info, b0.length);
				if (rtn.addAll(bAddUnEqualSizedList, pa2Element, list)) {
					add(rtn);
				} else {
					rtn = null;
				}
			}
		}
		return rtn;
	}

	public <R, B> DataTable addNewDataTable(String info, Processable2<R, Integer, B> pa2Element, List<B>... lists) {
		return addNewDataTable(info, false, pa2Element, lists);
	}

	public <B> DataTable addNewDataTable(String info, List<B>... lists) {
		return addNewDataTable(info, false, null, lists);
	}

	public <R> DataTable addNewDataTable(String info, Processable2<R, Integer, Double> pa2Element, double[]... arrays) {
		return addNewDataTable(info, false, pa2Element, arrays);
	}

	public DataTable addNewDataTable(String info, double[]... arrays) {
		return addNewDataTable(info, false, null, arrays);
	}

	public <R, B> DataTable addNewDataTable(String info, Processable2<R, Integer, B[]> pa2Element, List<B[]> list) {
		return addNewDataTable(info, false, pa2Element, list);
	}

	public <B> DataTable addNewDataTable(String info, List<B[]> list) {
		return addNewDataTable(info, false, null, list);
	}

	public int size() {
		return members.size();
	}

	/**
	 * 
	 * @param i
	 *            starts from 0
	 * @return
	 */
	public DataTable get(int i) {
		DataTable rtn = null;
		if (i >= 0 && i < members.size()) {
			rtn = members.get(i);
		}
		return rtn;
	}

	public boolean set(int i, DataTable dt) {
		boolean rtn = false;
		if (i >= 0 && i < members.size()) {
			rtn = true;
			members.set(i, dt);
		}
		return rtn;
	}

	public DataTable remove(int i) {
		return members.remove(i);
	}

	public DataTableSet clone() {
		DataTableSet rtn = new DataTableSet(info);
		for (int i = 0, mi = members.size(); i < mi; i++) {
			DataTable dt = members.get(i);
			rtn.add(dt.clone());
		}
		return rtn;
	}
}
