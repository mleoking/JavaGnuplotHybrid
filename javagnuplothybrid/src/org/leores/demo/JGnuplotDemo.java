package org.leores.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.leores.plot.JGnuplot;
import org.leores.plot.JGnuplot.Plot;
import org.leores.util.U;
import org.leores.util.able.Processable2;
import org.leores.util.data.DataTable;
import org.leores.util.data.DataTableSet;

public class JGnuplotDemo {
	Plot plot1, plot2;

	public JGnuplotDemo() {
		prepPlot();
	}

	public void plot2d() {
		JGnuplot jg = new JGnuplot() {
			{
				terminal = "pngcairo enhanced dashed";
				output = "plot2d.png";
			}
		};
		Plot plot = new Plot("") {
			{
				xlabel = "x";
				ylabel = "y";
			}
		};
		double[] x = { 1, 2, 3, 4, 5 }, y1 = { 2, 4, 6, 8, 10 }, y2 = { 3, 6, 9, 12, 15 };
		DataTableSet dts = plot.addNewDataTableSet("2D Plot");
		dts.addNewDataTable("y=2x", x, y1);
		dts.addNewDataTable("y=3x", x, y2);
		jg.execute(plot, jg.plot2d);
	}

	public void plot2dBar() {
		JGnuplot jg = new JGnuplot() {
			{
				terminal = "pngcairo enhanced dashed";
				output = "plot2dBar.png";
			}
		};
		Plot plot = new Plot("") {
			{
				xlabel = "x";
				ylabel = "y";
				yrange = "[0:15]";
				extra2 = "set key top left";
			}
		};
		double[] x = { 1, 2, 3, 4, 5 }, y1 = { 2, 4, 6, 8, 10 }, y2 = { 3, 6, 9, 12, 15 };
		DataTableSet dts = plot.addNewDataTableSet("2D Bar");
		DataTable dt = dts.addNewDataTable("", x, y1, y2);
		dt.insert(0, new String[] { "", "y1=2x", "y2=3x" });
		jg.execute(plot, jg.plot2dBar);
	}

	public void plot3d() {
		JGnuplot jg = new JGnuplot() {
			{
				terminal = "pngcairo enhanced dashed";
				output = "plot3d.png";
			}
		};
		Plot plot = new Plot("") {
			{
				xlabel = "x";
				ylabel = "y";
				zlabel = "z";
			}
		};
		double[] x = { 1, 2, 3, 4, 5 }, y = { 2, 4, 6, 8, 10 }, z = { 3, 6, 9, 12, 15 }, z2 = { 2, 8, 18, 32, 50 };
		DataTableSet dts = plot.addNewDataTableSet("3D Plot");
		dts.addNewDataTable("z=x+y", x, y, z);
		dts.addNewDataTable("z=x*y", x, y, z2);
		jg.execute(plot, jg.plot3d);
	}

	public void plotDensity() {
		JGnuplot jg = new JGnuplot() {
			{
				terminal = "pngcairo enhanced dashed";
				output = "plotDensity.png";
			}
		};
		Plot plot = new Plot("") {
			{
				xlabel = "x";
				ylabel = "y";
				zlabel = "z=x^2+y^2";
			}
		};
		DataTableSet dts = plot.addNewDataTableSet("Density Plot");
		//prepare data
		List x = new ArrayList(), y = new ArrayList(), z = new ArrayList();
		for (double i = -2; i <= 2; i += 0.5) {
			for (double j = -2; j <= 2; j += 0.5) {
				x.add(i);
				y.add(j);
				z.add(i * i + j * j);
			}
		}
		dts.addNewDataTable("z=x^2+y^2", x, y, z);
		jg.execute(plot, jg.plotDensity);
	}

	public void plotImage() {
		JGnuplot jg = new JGnuplot() {
			{
				terminal = "pngcairo enhanced dashed";
				output = "plotImage.png";
			}
		};
		Plot plot = new Plot("") {
			{
				xlabel = "x";
				ylabel = "y";
				zlabel = "z=x^2+y^2";
			}
		};
		DataTableSet dts = plot.addNewDataTableSet("Image Plot");
		//prepare data
		List x = new ArrayList(), y = new ArrayList(), z = new ArrayList();
		for (double i = -2; i <= 2; i += 0.5) {
			for (double j = -2; j <= 2; j += 0.5) {
				x.add(i);
				y.add(j);
				z.add(i * i + j * j);
			}
		}
		dts.addNewDataTable("z=x^2+y^2", x, y, z);
		jg.execute(plot, jg.plotImage);
	}

	public void simple() {
		plot2d();
		plot2dBar();
		plot3d();
		plotDensity();
		plotImage();
	}

	public void prepPlot() {
		plot1 = new Plot("plot1") {
			{
				xlabel = "x axis";
				ylabel = "y axis";
				extra2 = "set key top left";
			}
		};
		//DataTableSet 1 2d add data one by one
		DataTableSet dts1 = plot1.addNewDataTableSet("DataTableSet 2d");//use null to avoid the output of figure title for this dataset.
		DataTable dt1 = dts1.addNewDataTable("x", 2);
		DataTable dt2 = dts1.addNewDataTable("2x", 2);
		DataTable dt3 = dts1.addNewDataTable("3x", 2);
		DataTable dt4 = dts1.addNewDataTable("4x", 2);
		DataTable dt5 = dts1.addNewDataTable("5x", 2);
		DataTable dt6 = dts1.addNewDataTable("6x", 2);
		DataTable dt7 = dts1.addNewDataTable("7x", 2);
		DataTable dt8 = dts1.addNewDataTable("8x", 2);
		DataTable dt9 = dts1.addNewDataTable("9x", 2);
		DataTable dt10 = dts1.addNewDataTable("10x", 2);
		for (int i = 0; i < 5; i++) {
			dt1.add(i, i);
			dt2.add(i, 2 * i);
			dt3.add(i, 3 * i);
			dt4.add(i, 4 * i);
			dt5.add(i, 5 * i);
			dt6.add(i, 6 * i);
			dt7.add(i, 7 * i);
			dt8.add(i, 8 * i);
			dt9.add(i, 9 * i);
			dt10.add(i, 10 * i);
		}
		//DataTableSet 2 3d add data using prepared lists
		DataTableSet dts2 = plot1.addNewDataTableSet("DataTableSet 3d");
		List x = new ArrayList(), y = new ArrayList(), z1 = new ArrayList(), z2 = new ArrayList();
		for (double i = -2; i <= 2; i += 0.5) {
			for (double j = -2; j <= 2; j += 0.5) {
				x.add(i);
				y.add(j);
				z1.add(i * i + j * j);
				z2.add(4 + i * i + j * j);
			}
		}
		Processable2 pa2 = new Processable2.SampleListByIndex(3);
		dts2.addNewDataTable("x^2+y^2", pa2, x, y, z1);
		dts2.addNewDataTable("4+x^2+y^2", x, y, z2);
		DataTableSet dts3 = dts1;
		plot1.add(dts3);

		plot2 = new Plot("plot2");
		plot2.load("xlabel=x axis;ylabel=y axis;zlabel=z axis");
		plot2.add(dts2);
	}

	/**
	 * Output the generated gnuplot script file without executing it.
	 */
	public void compile() {
		JGnuplot jg = new JGnuplot();
		jg.compile(plot1, jg.plot2d);//if file name is omitted it will use plot.info+".plt" as the file name.
		jg.compile(plot2, jg.plot3d);
		jg.compile(plot2, jg.plotDensity, "plot2density.plt");
		jg.compile(plot1, jg.multiplot, "jgnuplot3.plt");
	}

	/**
	 * Shows how to use both synchronized and asynchronized running of Gnuplot.
	 * (Synchronized: your java program will wait until you close the popped
	 * Gnuplot window; Asynchronized: you java program will not wait.)
	 */
	public void execute() {
		JGnuplot jg = new JGnuplot();
		jg.terminal = "wxt enhanced";
		jg.execute(plot1, jg.plot2d, jg.JG_InNewThread | jg.JG_Pause);
		jg.execute(plot2, jg.plot3d);
		jg.beforeStyleVar = "ls1=10;ls10=1;ls2=9;ls9=2;";
		jg.executeA(plot1, jg.plot2d);//Asynchronous plot in a new thread
		jg.beforeStyleVar = null;
		jg.extra = "set label \"Dynamically add extra code using the extra field.\" at graph 0.5,0.5 center";
		//jg.extra = "set style line 1 lc rgbcolor 'greenyellow' lt 1 lw 2 pt 1";
		jg.execute(plot1, jg.plot2d);
		jg.execute(plot2, jg.plot3d);
		jg.terminal = "wxt enhanced size 800,600;";
		jg.execute(plot1, jg.multiplot);//show gnuplot warning in the java console. warning can be easily solved by extending the canvas size		
	}

	/**
	 * Show different available terminals to plot. Please refer to the Gnuplot
	 * website for the complete list of terminals.
	 */
	public void terminals() {
		JGnuplot jg = new JGnuplot();
		jg.execute(plot1, jg.plot2d);//Using the default terminal
		//windows terminal is only available to windows. You might get error output from gnuplot if you are not using windows.		
		jg.terminal = "windows enhanced dashed title 'id=100 hello there' size 600,600";
		jg.beforeStyle = "linewidth=4";
		jg.execute(plot1, jg.plot2d, ~jg.JG_DeleteTempFile);
		jg.terminal = null;//Set the terminal to the default terminal
		jg.execute(plot1, jg.plot2d);//wxt terminal default size 640,384
		jg.terminal = "dumb";//ascii art terminal for anything that prints text
		jg.execute(plot1, jg.plot2d);
		jg.terminal = "jpeg enhanced size 600,600";
		jg.output = "plot1.jpg";
		jg.execute(plot1, jg.plot2d);
		jg.terminal = "pngcairo enhanced dashed size 600,600";
		jg.output = "plot1.png";
		jg.execute(plot1, jg.plot2d);
		jg.output = "plot2.png";
		jg.execute(plot2, jg.plot3d);
		//the size unit for pdf is Inch. It is different from other terminals. The default pdf size is 5,3.
		jg.terminal = "pdfcairo enhanced dashed size 6,6";
		jg.output = "plot1.pdf";
		jg.execute(plot1, jg.plot2d);
		jg.output = "plot2.pdf";
		jg.execute(plot2, jg.plot3d);
		jg.output = "plot3.pdf";
		jg.execute(plot1, jg.multiplot);
	}

	/**
	 * Customised plot functions.
	 */
	public void plotx() {
		//way1: through the JGunplot.plotx field.
		JGnuplot jg = new JGnuplot();
		jg.plotx = "$header$\n plot for [i=1:$size(1)$] '-' title info2(1,i).' plotx' w lp ls i\n $data(1,2d)$";
		jg.execute(plot1, jg.plotx);
		//way2: through a sub class extending JGunplot
		class JGnuplot2 extends JGnuplot {
			String myplot, rawcode;//No need to declare public/protected/private for these fields.

			public void initialize() {
				String sFLoad2 = "jgnuplot2.xml";
				//U.copyFileFromClassPath(this, sFLoad2, sFLoad2, false);//this also works in a jar file
				U.loadFromXML(this, sFLoad2, false);
			}
		}
		JGnuplot2 jg2 = new JGnuplot2();
		jg2.execute(plot1, jg2.myplot);
		jg2.execute(new Plot(null), jg2.rawcode);
	}

	public static void demo() {
		JGnuplotDemo jgd = new JGnuplotDemo();
		jgd.simple();
		jgd.compile();
		jgd.execute();
		jgd.terminals();
		jgd.plotx();
	}

	/**
	 * Set the working directory to be ${workspace_loc:leotask/demo} in Eclipse.
	 * In other IDEs set the working directory to the "demo" folder.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		U.tLog("Set the working directory to be ${workspace_loc:javagnuplothybrid/demo} in Eclipse. In other IDEs set the working directory to the \"demo\" folder.");
		U.tLog("------------------");
		JGnuplotDemo.demo();
	}
}
