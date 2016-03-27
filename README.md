##JavaGnuplotHybrid

A hybrid programming framework for writing Gnuplot code in java. The latest version of JavaGnuplotHybrid has been intergrated into [**LeoTask**](https://github.com/mleoking/LeoTask).

The JavaGnuplotHybrid folder is an eclipse project. You can download and import it into your eclipse workspace.

[**Download the demo**](javagnuplothybrid/demo/javagnuplothybrid.jar?raw=true)|[**See the examples**](javagnuplothybrid/doc/examples.md)

## Demo

[**Download the demo**](javagnuplothybrid/demo/javagnuplothybrid.jar?raw=true) Double click the demo to run it. [Here is the code of the demo](javagnuplothybrid/src/org/leores/demo/JGnuplotDemo.java).

Before running, make sure, you have Gnuplot installed and its path included in the system variable: PATH.

Windows system users can download, unzip, and install (install.bat) the all-in-one runtime package: [**LeoTaskRunEnv**](https://github.com/mleoking/LeoTaskApp/releases/download/v1.0.0/LeoTaskRunEnv.zip)

## Description

JavaGnuplotHybrid is a light-weight library for plotting data in Java using Gnuplot.

This framework does not aim to implement a comprehensive sets of Java functions to cover all Gnuplot features. It rather promotes hybrid programming with Java and Gnuplot. The framework only does what Java is good at: data processing, functions, variables, etc..

It keeps its footprint in Gnuplot code to the minimum. So that, without this framework, you program code can be still reused in Gnuplot with ease. There is no vendor lock-in.

Thanks to this hybrid programming mode, the framework can support all features of the current and future versions of Gnuplot. There is no need to worry about whether this framework can keep up with the updating speed of Gnuplot. Because all necessary functions for the hybrid programming mode are done.

## Example

Here is the corresponding java code to produce a simple plot:

```java
	public void plot2d() {
		JGnuplot jg = new JGnuplot();
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
```	
It produces the following plot:

![2D Plot](javagnuplothybrid/doc/plot2d.png)

In addition a file named _jgnuplot.xml_ is generated. It is the plot style file coded in Gnuplot script. You can modify the _plot2d_ section in _jgnuplot.xml_ to change the ploting styles. The framework uses tags warped in $$ in _jgnuplot.xml_ to represent variables/methods/expressions in Java. Here is a simple _plot2d_ section:

```xml
<plot2d>~
    $style2d$
    $header$ 
    set title "$info(1)$";
    plot for [i=1:$size(1)$] '-' title info2(1,i) w lp ls ls(i);
    $data(1,2d)$
</plot2d>
```
Here, $style2d$ and $header$ refers to the style2d and header sections in the _jgnuplot.xml_. $info(1)$ calls the built-in function of _public String info(String dataTableSetNum)_ in [JGnuplot.java](javagnuplothybrid/src/org/leores/plot/JGnuplot.java) with the parameter of "1" to output the "2D Plot" text in the previous example java code. $data(1,2d)$ calls the build-in method _public String data(String dataTableSetNum, String type)_ in [JGnuplot.java](javagnuplothybrid/src/org/leores/plot/JGnuplot.java) to output the data of the plot in 2d format.

[**Click here for more examples**](javagnuplothybrid/doc/examples.md)

## Features:

* Hybrid programming with Java and Gnuplot
* Very light weight (just three core Classes)
* Use tags in Gnuplot code to execute functions or get fields' values in Java.
* Support both synchronized and asynchronized running of Gnuplot in Java. (synchronized: your java program will wait until you close the popped Gnuplot window; asynchronized: you java program will not wait.)
* Capture error/normal text output of Gnuplot to the java terminal
* Read Gnuplot code from xml files
* Support Gnuplot code template.
* More attractive plot styles

## Example Figures:

![2d](https://upload.wikimedia.org/wikipedia/commons/3/34/A_typical_2d_plot_produced_using_JavaGnuplotHybrid.png)
![3d](https://upload.wikimedia.org/wikipedia/commons/c/cd/A_typical_3d_plot_produced_using_JavaGnuplotHybrid.png)




[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/mleoking/javagnuplothybrid/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

