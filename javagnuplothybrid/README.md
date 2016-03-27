##JavaGnuplotHybrid

A hybrid programming framework for writing Gnuplot code in java.

The JavaGnuplotHybrid folder is an eclipse project. You can download and import it into your eclipse workspace.

## Demo

Run the JavaGnuplotHybrid.jar inside JavaGnuplotHybrid folder to see the demo. 

Before running, make sure, you have Gnuplot installed and its path included in the system variable: PATH.

## Description

JavaGnuplotHybrid is a light-weight library for plotting data in Java using Gnuplot.

This framework does not aim to implement a comprehensive sets of Java functions to cover all Gnuplot features. It rather promotes hybrid programming with Java and Gnuplot. The framework only does what Java is good at: data processing, functions, variables, etc..

It keeps its footprint in Gnuplot code to the minimum. So that, without this framework, you program code can be still reused in Gnuplot with ease. There is no vendor lock-in.

Thanks to this hybrid programming mode, the framework can support all features of the current and future versions of Gnuplot. There is no need to worry about whether this framework can keep up with the updating speed of Gnuplot. Because all necessary functions for the hybrid programming mode are done.

## Example
The framework uses tags in Gnuplot code to represent variables/methods/expressions in Java. 

Here is a simple plot2d Gnuplot code template using the framework:

    $style2d$
    $header$ 
    set title "$info(1)$";
    plot for [i=1:$size(1)$] '-' title info2(1,i) w lp ls ls(i);
    $data(1,2d)$

Here, $header$ refers to the value of the "header" field in a Java object. $info(1)$ will call the info method of a Java Object with parameter "1" (String). data(1,2d) is a build-in method in the framework to output the data of the plot in 2d format.

Here is the corresponding java code to produce a simple plot:

    JGnuplot jg = new JGnuplot();
    Plot plot0 = new Plot() {
        String xlabel = "'x'", ylabel = "'y'";
    };
    double[] x = { 1, 2, 3, 4, 5 }, y1 = { 2, 4, 6, 8, 10 }, y2 = { 3, 6, 9, 12, 15 };
    DataTableSet dts = plot0.addNewDataTableSet("Simple plot");
    dts.addNewDataTable("2x", x, y1);
    dts.addNewDataTable("3x", x, y2);
    jg.execute(plot0, jg.plot2d);
	
## Features:

* Hybrid programming with Java and Gnuplot
* Very light weight (just three core Classes)
* Use tags in Gnuplot code to execute functions or get fields' values in Java.
* Support both synchronized and asynchronized running of Gnuplot in Java. (synchronized: your java program will wait until you close the popped Gnuplot window; asynchronized: you java program will not wait.)
* Capture error/normal text output of Gnuplot to the java terminal
* Read Gnuplot code from xml files
* Support Gnuplot code template.
* More attractive plot styles
