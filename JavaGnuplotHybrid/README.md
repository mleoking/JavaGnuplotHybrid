##JavaGnuplotHybrid

A hybrid programming framwork for writing gnuplot code in java.

## Demo

Run the JavaGnuplotHybrid.jar inside JavaGnuplotHybrid folder to see the demo. 

Before running, makre sure, you have gnuplot installed and its path included in the system variable: PATH.

## Description

JavaGnuplotHybrid is a light-weight library for plotting data in Java using gnuplot.

This framework does not aim to implement a comprehensive sets of Java functions to cover all gnuplot features. It rather promotes hybrid programming with Java and Gnuplot. The framework only does what Java is good at: data processing, functions, variables, etc..

It keeps its footprint in gnuplot code to the minumum. So that, without this framework, you program code can be still resued in gnuplot with ease. There is no vendor lock-in.

Thanks to this hybrid programming mode, the framework can support all features of the current and future versions of gnuplot. There is no need to worry about whether this framework can keep up with the updating speed of gnuplot. Because all necessary functions for the hybrid programming mode are done.

## Example
The framework uses tags in gnuplot code to represent variables/methods/expressions in Java. 

Here is a simple plot2d gnuplot code using the framwork:

    $header$
    $style2d$
    set title "$info(1)$"
    plot for [i=1:$size(1)$] '-' title info2(1,i) w lp ls i
    $data2d(1)$

Here, $header$ refers to the value of the "header" field in a Java object. $info(1)$ will call the info method of a Java Object with parameter "1" (String). data2d(index) is a build-in method in the framework to output the data of the plot in 2d format.

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
* Use tags in gnuplot code to execute functions or get fields' values in Java.
* Support both synchronized and asynchronized running of gnuplot in Java. (synchronized: your java program will wait until you close the popped gnuplot window; asynchronized: you java program will not wait.)
* Capture error/normal text output of gnuplot to the java terminal
* Read gnuplot code from xml files
* Support gnuplot code template.
