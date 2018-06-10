## JavaGnuplotHybrid Examples

## 2D Plot

![2D Plot](plot2d.png)

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

## 2D Bar Plot

![2D Bar Plot](plot2dBar.png)

```java
	public void plot2dBar() {
		JGnuplot jg = new JGnuplot();
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
```

## 3D Plot

![3D Plot](plot3d.png)

```java
	public void plot3d() {
		JGnuplot jg = new JGnuplot();
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
```

## Density Plot

![Density Plot](plotDensity.png)

```java
	public void plotDensity() {
		JGnuplot jg = new JGnuplot();
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
```

## Image Plot

![Density Plot](plotImage.png)

```java
	public void plotImage() {
		JGnuplot jg = new JGnuplot();
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
```
