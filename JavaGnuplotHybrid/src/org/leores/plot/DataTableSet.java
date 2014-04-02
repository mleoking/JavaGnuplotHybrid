package org.leores.plot;

import java.util.ArrayList;
import java.util.List;

public class DataTableSet {
	public String info;
	public List<DataTable> members;

	public DataTableSet(String info) {
		this.info = info;
		members = new ArrayList<DataTable>();
	}

	public boolean add(DataTable dataTable) {
		boolean rtn = false;
		if (dataTable != null) {
			rtn = true;
			members.add(dataTable);
		}
		return rtn;
	}

	public DataTable addNewDataTable(String info, int nColumn) {
		DataTable rtn = new DataTable(info, nColumn);
		add(rtn);
		return rtn;
	}

	public DataTable addNewDataTable(String info, boolean bAddUnEqualSizedList, List... lists) {
		DataTable rtn = new DataTable(info, lists.length);
		if (rtn.addAll(bAddUnEqualSizedList, lists)) {
			add(rtn);
		} else {
			rtn = null;
		}
		return rtn;
	}

	public DataTable addNewDataTable(String info, boolean bAddUnEqualSizedList, double[]... arrays) {
		DataTable rtn = new DataTable(info, arrays.length);
		if (rtn.addAll(bAddUnEqualSizedList, arrays)) {
			add(rtn);
		} else {
			rtn = null;
		}
		return rtn;
	}

	public DataTable addNewDataTable(String info, List... lists) {
		return addNewDataTable(info, false, lists);
	}

	public DataTable addNewDataTable(String info, double[]... arrays) {
		return addNewDataTable(info, false, arrays);
	}

	public int size() {
		return members.size();
	}

	public DataTable get(int i) {
		return members.get(i);
	}

	public DataTable remove(int i) {
		return members.remove(i);
	}
}
