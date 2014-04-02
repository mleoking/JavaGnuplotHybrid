package org.leores.plot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.leores.util.Demo;
import org.leores.util.U;
import org.leores.plot.JGnuplot.*;

public class JGnuplotDemo extends Demo {
	Plot plot1, plot2;

	public boolean initialize() {
		boolean rtn = super.initialize();
		prepPlot();
		return rtn;
	}
	
	public void simple() {
		JGnuplot jg = new JGnuplot();
		Plot plot0 = new Plot() {
			String xlabel = "'x'", ylabel = "'y'";
		};
		double[] x = { 1, 2, 3, 4, 5 }, y1 = { 2, 4, 6, 8, 10 }, y2 = { 3, 6, 9, 12, 15 };
		DataTableSet dts = plot0.addNewDataTableSet("Simple plot");
		dts.addNewDataTable("2x", x, y1);
		dts.addNewDataTable("3x", x, y2);
		jg.execute(plot0, jg.plot2d);
	}

	public void prepPlot() {
		plot1 = new Plot() {//No need to declare public/protected/private for these fields.
			//String terminal, output, xrange, yrange, zrange;
			//Quotation inside is necessary for xlabel and other fields cause you could add more config code
			String xlabel = "'x axis' textcolor rgbcolor 'dark-red'", ylabel = "'y axis'";
		};
		//DataTableSet 1 2d add data one by one
		DataTableSet dts1 = plot1.addNewDataTableSet("DataTableSet 2d");//use null to avoid the output of figure title for this dataset.
		DataTable dt1 = dts1.addNewDataTable("x", 2);
		DataTable dt2 = dts1.addNewDataTable("x^2", 2);
		DataTable dt3 = dts1.addNewDataTable("x^3", 2);
		for (int i = 0; i < 5; i++) {
			dt1.add(i, i);
			dt2.add(i, i * i);
			dt3.add(i, i * i * i);
		}
		//DataTableSet 2 3d add data using prepared lists
		DataTableSet dts2 = plot1.addNewDataTableSet("DataTableSet 3d");
		List x = new ArrayList(), y = new ArrayList(), z1 = new ArrayList(), z2 = new ArrayList(), z3 = new ArrayList();
		for (double i = 0; i < 1; i += 0.1) {
			for (double j = 0; j < 1; j += 0.1) {
				x.add(i);
				y.add(j);
				z1.add(i + j);
				z2.add(Math.exp(i + j));
				z3.add(Math.log(i + j));
			}
		}
		dts2.addNewDataTable("x+y", x, y, z1);
		dts2.addNewDataTable("e^{(x+y)}", x, y, z2);
		dts2.addNewDataTable("ln(i + j)", x, y, z3);
		DataTableSet dts3 = dts1;
		plot1.add(dts3);

		plot2 = new Plot() {
			String zlabel = "'z axis'", zrange = "[-3:8]";
		};
		plot2.add(dts2);
	}

	public void compile() {
		JGnuplot jg = new JGnuplot();
		jg.compile(plot1, jg.plot2d, fo("gnuplot1.plt"));
		jg.compile(plot2, jg.plot3d, fo("gnuplot2.plt"));
		jg.compile(plot1, jg.multiplot, fo("gnuplot3.plt"));
	}

	public void execute() {
		JGnuplot jg = new JGnuplot() {
			String terminal = "wxt enhanced";
		};
		jg.execute(plot1, jg.plot2d, jg.JG_InNewThread | jg.JG_Pause);
		jg.execute(plot2, jg.plot3d);
		jg.executeA(plot1, jg.plot2d);//asynchronous plot in a new thread 
		jg.extra = "set label \"Dynamically add extra code using the extra field.\" at graph 0.5,0.5 center";
		jg.execute(plot1, jg.plot2d);
		jg.execute(plot2, jg.plot3d);
		jg.execute(plot1, jg.multiplot);//show gnuplot warning in the java console. warning can be easily solved by extending the canvas size		
	}

	public void terminals() {
		class JGnuplot2 extends JGnuplot {
			//windows terminal is only available to windows. You might get error output from gnuplot if you are not using windows.
			String terminal = "windows enhanced title 'id=100 hello there' size 600,600";
			String output = null;
		}
		JGnuplot2 jg = new JGnuplot2();
		jg.execute(plot1, jg.plot2d, ~jg.JG_DeleteTempFile);
		jg.terminal = null;
		jg.execute(plot1, jg.plot2d);
		jg.terminal = "dumb";//ascii art terminal for anything that prints text
		jg.execute(plot1, jg.plot2d);
		jg.terminal = "jpeg enhanced size 600,600";
		jg.output = "'plot1.jpg'";
		jg.execute(plot1, jg.plot2d);
		jg.terminal = "pngcairo enhanced size 600,600";
		jg.output = "'plot1.png'";
		jg.execute(plot1, jg.plot2d);
		//the size unit for pdf is Inch. It is different from other terminals. The default pdf size is 5,3.
		jg.terminal = "pdfcairo enhanced size 6,6";
		jg.output = "'plot1.pdf'";
		jg.execute(plot1, jg.plot2d);
		jg.output = "'plot2.pdf'";
		jg.execute(plot2, jg.plot3d);
		jg.output = "'plot3.pdf'";
		jg.execute(plot1, jg.multiplot);
	}

	/**
	 * Customised plot functions.
	 */
	public void plotx() {
		//way1: through the JGunplot.plotx field.
		JGnuplot jg = new JGnuplot();
		jg.plotx = "$header$\n plot for [i=1:$size(1)$] '-' title info2(1,i).' plotx' w lp ls i\n $data2d(1)$";
		jg.execute(plot1, jg.plotx);
		//way2: through a sub class extending JGunplot
		class JGnuplot2 extends JGnuplot {
			public String myplot, rawcode, sFLoad2;

			public boolean initialize() {
				sFLoad2 = "jgnuplot2.xml";
				U.copyFileFromClassPath(this, sFLoad2, sFLoad2, false);//this also works in a jar file
				return U.loadFromXML(this, sFLoad2, false);
			}
		}
		JGnuplot2 jg2 = new JGnuplot2();
		jg2.execute(plot1, jg2.myplot);
		jg2.execute(new Plot(), jg2.rawcode);
	}

	public static void main(String[] args) {
		JGnuplotDemo jgd = new JGnuplotDemo();
		jgd.simple();
		jgd.compile();
		jgd.execute();
		jgd.terminals();		
		jgd.plotx();		
	}
}
