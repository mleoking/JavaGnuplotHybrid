##JavaGnuplotHybrid Examples

## 2D Plot

![2D Plot](javagnuplothybrid/demo/plot2d.png)

```java
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
```
