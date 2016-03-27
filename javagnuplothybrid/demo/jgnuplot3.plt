ls(i)=value(sprintf("ls%i",i));lt(i)=value(sprintf("lt%i",i));pt(i)=value(sprintf("pt%i",i));lc(i)=value(sprintf("lc%i",i));
lw=2;ps=1;pi=1;axiscolor='gray30';gridcolor='gray30';clw=1;
lc1='dark-blue';lc2='dark-red';lc3='dark-green';lc4='orange';lc5='dark-pink';lc6='purple';lc7='olive';lc8='slategray';lc9='steelblue';lc10='black';
lt1=1;lt2=2;lt3=3;lt4=4;lt5=5;lt6=6;lt7=7;lt8=8;lt9=9;lt10=10;
pt1=5;pt2=7;pt3=9;pt4=11;pt5=13;pt6=15;pt7=17;pt8=19;pt9=21;pt10=23;
ls1=1;ls2=2;ls3=3;ls4=4;ls5=5;ls6=6;ls7=7;ls8=8;ls9=9;ls10=10;ls11=11;ls12=12;ls13=13;ls14=14;ls15=15;ls16=16;ls17=17;ls18=18;ls19=19;ls20=20;
lw1=lw;lw2=lw;lw3=lw;lw4=lw;lw5=lw;lw6=lw;lw7=lw;lw8=lw;lw9=lw;lw10=lw;
ps1=ps;ps2=ps;ps3=ps;ps4=ps;ps5=ps;ps6=ps;ps7=ps;ps8=ps;ps9=ps;ps10=ps;
pi1=pi;pi2=pi;pi3=pi;pi4=pi;pi5=pi;pi6=pi;pi7=pi;pi8=pi;pi9=pi;pi10=pi;
#Line styles:
set style line 1 lc rgb lc1 lt lt1 lw lw1 ps ps1 pt pt1 pi pi1;
set style line 2 lc rgb lc2 lt lt2 lw lw2 ps ps2 pt pt2 pi pi2;
set style line 3 lc rgb lc3 lt lt3 lw lw3 ps ps3 pt pt3 pi pi3;
set style line 4 lc rgb lc4 lt lt4 lw lw4 ps ps4 pt pt4 pi pi4;
set style line 5 lc rgb lc5 lt lt5 lw lw5 ps ps5 pt pt5 pi pi5;
set style line 6 lc rgb lc6 lt lt6 lw lw6 ps ps6 pt pt6 pi pi6;
set style line 7 lc rgb lc7 lt lt7 lw lw7 ps ps7 pt pt7 pi pi7;
set style line 8 lc rgb lc8 lt lt8 lw lw8 ps ps8 pt pt8 pi pi8;
set style line 9 lc rgb lc9 lt lt9 lw lw9 ps ps9 pt pt9 pi pi9;
set style line 10 lc rgb lc10 lt lt10 lw lw10 ps ps10 pt pt10 pi pi10;
set style line 101 lc rgb axiscolor lt 1 lw 1;
set style line 102 lc rgb gridcolor lt 0 lw 1;
#End common style
#Define axis
set border 3 back ls 101;# remove border on top and right and set color to gray
set tics nomirror;
set grid back ls 102;
set termoption dashed;
#Header start
#set macros;#Enable the use of macros
set terminal wxt size 480,288;#corresponding to the default size of pdf terminal 5in,3in.
set terminal wxt title "plot1";
set xlabel "x axis";
set ylabel "y axis";
info1(i)=value(sprintf("info%i",i));
info2(i,j)=value(sprintf("info%i_%i",i,j));
info1="DataTableSet 2d";
info1_1="x";
info1_2="2x";
info1_3="3x";
info1_4="4x";
info1_5="5x";
info1_6="6x";
info1_7="7x";
info1_8="8x";
info1_9="9x";
info1_10="10x";
info2="DataTableSet 3d";
info2_1="x^2+y^2";
info2_2="4+x^2+y^2";
info3="DataTableSet 2d";
info3_1="x";
info3_2="2x";
info3_3="3x";
info3_4="4x";
info3_5="5x";
info3_6="6x";
info3_7="7x";
info3_8="8x";
info3_9="9x";
info3_10="10x";
set key top left
#Header end
set multiplot layout 3,1;
set title "DataTableSet 2d";
plot for [i=1:10] '-' title info2(1,i) w lp ls ls(i);
0 0
1 1
2 2
3 3
4 4
e
0 0
1 2
2 4
3 6
4 8
e
0 0
1 3
2 6
3 9
4 12
e
0 0
1 4
2 8
3 12
4 16
e
0 0
1 5
2 10
3 15
4 20
e
0 0
1 6
2 12
3 18
4 24
e
0 0
1 7
2 14
3 21
4 28
e
0 0
1 8
2 16
3 24
4 32
e
0 0
1 9
2 18
3 27
4 36
e
0 0
1 10
2 20
3 30
4 40
e
set title "DataTableSet 3d";
plot for [i=1:2] '-' title info2(2,i) w lp ls ls(i);
-2.0 -2.0 8.0
-2.0 -0.5 4.25
-2.0 1.0 5.0
-1.5 -2.0 6.25
-1.5 -0.5 2.5
-1.5 1.0 3.25
-1.0 -2.0 5.0
-1.0 -0.5 1.25
-1.0 1.0 2.0
-0.5 -2.0 4.25
-0.5 -0.5 0.5
-0.5 1.0 1.25
0.0 -2.0 4.0
0.0 -0.5 0.25
0.0 1.0 1.0
0.5 -2.0 4.25
0.5 -0.5 0.5
0.5 1.0 1.25
1.0 -2.0 5.0
1.0 -0.5 1.25
1.0 1.0 2.0
1.5 -2.0 6.25
1.5 -0.5 2.5
1.5 1.0 3.25
2.0 -2.0 8.0
2.0 -0.5 4.25
2.0 1.0 5.0
e
-2.0 -2.0 12.0
-2.0 -1.5 10.25
-2.0 -1.0 9.0
-2.0 -0.5 8.25
-2.0 0.0 8.0
-2.0 0.5 8.25
-2.0 1.0 9.0
-2.0 1.5 10.25
-2.0 2.0 12.0
-1.5 -2.0 10.25
-1.5 -1.5 8.5
-1.5 -1.0 7.25
-1.5 -0.5 6.5
-1.5 0.0 6.25
-1.5 0.5 6.5
-1.5 1.0 7.25
-1.5 1.5 8.5
-1.5 2.0 10.25
-1.0 -2.0 9.0
-1.0 -1.5 7.25
-1.0 -1.0 6.0
-1.0 -0.5 5.25
-1.0 0.0 5.0
-1.0 0.5 5.25
-1.0 1.0 6.0
-1.0 1.5 7.25
-1.0 2.0 9.0
-0.5 -2.0 8.25
-0.5 -1.5 6.5
-0.5 -1.0 5.25
-0.5 -0.5 4.5
-0.5 0.0 4.25
-0.5 0.5 4.5
-0.5 1.0 5.25
-0.5 1.5 6.5
-0.5 2.0 8.25
0.0 -2.0 8.0
0.0 -1.5 6.25
0.0 -1.0 5.0
0.0 -0.5 4.25
0.0 0.0 4.0
0.0 0.5 4.25
0.0 1.0 5.0
0.0 1.5 6.25
0.0 2.0 8.0
0.5 -2.0 8.25
0.5 -1.5 6.5
0.5 -1.0 5.25
0.5 -0.5 4.5
0.5 0.0 4.25
0.5 0.5 4.5
0.5 1.0 5.25
0.5 1.5 6.5
0.5 2.0 8.25
1.0 -2.0 9.0
1.0 -1.5 7.25
1.0 -1.0 6.0
1.0 -0.5 5.25
1.0 0.0 5.0
1.0 0.5 5.25
1.0 1.0 6.0
1.0 1.5 7.25
1.0 2.0 9.0
1.5 -2.0 10.25
1.5 -1.5 8.5
1.5 -1.0 7.25
1.5 -0.5 6.5
1.5 0.0 6.25
1.5 0.5 6.5
1.5 1.0 7.25
1.5 1.5 8.5
1.5 2.0 10.25
2.0 -2.0 12.0
2.0 -1.5 10.25
2.0 -1.0 9.0
2.0 -0.5 8.25
2.0 0.0 8.0
2.0 0.5 8.25
2.0 1.0 9.0
2.0 1.5 10.25
2.0 2.0 12.0
e
set title "DataTableSet 2d";
plot for [i=1:10] '-' title info2(3,i) w lp ls ls(i);
0 0
1 1
2 2
3 3
4 4
e
0 0
1 2
2 4
3 6
4 8
e
0 0
1 3
2 6
3 9
4 12
e
0 0
1 4
2 8
3 12
4 16
e
0 0
1 5
2 10
3 15
4 20
e
0 0
1 6
2 12
3 18
4 24
e
0 0
1 7
2 14
3 21
4 28
e
0 0
1 8
2 16
3 24
4 32
e
0 0
1 9
2 18
3 27
4 36
e
0 0
1 10
2 20
3 30
4 40
e
unset multiplot;
ls(i)=value(sprintf("ls%i",i));lt(i)=value(sprintf("lt%i",i));pt(i)=value(sprintf("pt%i",i));lc(i)=value(sprintf("lc%i",i));
lw=2;ps=1;pi=1;axiscolor='gray30';gridcolor='gray30';clw=1;
lc1='dark-blue';lc2='dark-red';lc3='dark-green';lc4='orange';lc5='dark-pink';lc6='purple';lc7='olive';lc8='slategray';lc9='steelblue';lc10='black';
lt1=1;lt2=2;lt3=3;lt4=4;lt5=5;lt6=6;lt7=7;lt8=8;lt9=9;lt10=10;
pt1=5;pt2=7;pt3=9;pt4=11;pt5=13;pt6=15;pt7=17;pt8=19;pt9=21;pt10=23;
ls1=1;ls2=2;ls3=3;ls4=4;ls5=5;ls6=6;ls7=7;ls8=8;ls9=9;ls10=10;ls11=11;ls12=12;ls13=13;ls14=14;ls15=15;ls16=16;ls17=17;ls18=18;ls19=19;ls20=20;
lw1=lw;lw2=lw;lw3=lw;lw4=lw;lw5=lw;lw6=lw;lw7=lw;lw8=lw;lw9=lw;lw10=lw;
ps1=ps;ps2=ps;ps3=ps;ps4=ps;ps5=ps;ps6=ps;ps7=ps;ps8=ps;ps9=ps;ps10=ps;
pi1=pi;pi2=pi;pi3=pi;pi4=pi;pi5=pi;pi6=pi;pi7=pi;pi8=pi;pi9=pi;pi10=pi;
#Line styles:
set style line 1 lc rgb lc1 lt lt1 lw lw1 ps ps1 pt pt1 pi pi1;
set style line 2 lc rgb lc2 lt lt2 lw lw2 ps ps2 pt pt2 pi pi2;
set style line 3 lc rgb lc3 lt lt3 lw lw3 ps ps3 pt pt3 pi pi3;
set style line 4 lc rgb lc4 lt lt4 lw lw4 ps ps4 pt pt4 pi pi4;
set style line 5 lc rgb lc5 lt lt5 lw lw5 ps ps5 pt pt5 pi pi5;
set style line 6 lc rgb lc6 lt lt6 lw lw6 ps ps6 pt pt6 pi pi6;
set style line 7 lc rgb lc7 lt lt7 lw lw7 ps ps7 pt pt7 pi pi7;
set style line 8 lc rgb lc8 lt lt8 lw lw8 ps ps8 pt pt8 pi pi8;
set style line 9 lc rgb lc9 lt lt9 lw lw9 ps ps9 pt pt9 pi pi9;
set style line 10 lc rgb lc10 lt lt10 lw lw10 ps ps10 pt pt10 pi pi10;
set style line 101 lc rgb axiscolor lt 1 lw 1;
set style line 102 lc rgb gridcolor lt 0 lw 1;
#End common style
#Define axis
set border 3 back ls 101;# remove border on top and right and set color to gray
set tics nomirror;
set grid back ls 102;
set termoption dashed;
#Header start
#set macros;#Enable the use of macros
set terminal wxt size 480,288;#corresponding to the default size of pdf terminal 5in,3in.
set terminal wxt title "plot1";
set xlabel "x axis";
set ylabel "y axis";
info1(i)=value(sprintf("info%i",i));
info2(i,j)=value(sprintf("info%i_%i",i,j));
info1="DataTableSet 2d";
info1_1="x";
info1_2="2x";
info1_3="3x";
info1_4="4x";
info1_5="5x";
info1_6="6x";
info1_7="7x";
info1_8="8x";
info1_9="9x";
info1_10="10x";
info2="DataTableSet 3d";
info2_1="x^2+y^2";
info2_2="4+x^2+y^2";
info3="DataTableSet 2d";
info3_1="x";
info3_2="2x";
info3_3="3x";
info3_4="4x";
info3_5="5x";
info3_6="6x";
info3_7="7x";
info3_8="8x";
info3_9="9x";
info3_10="10x";
set key top left
#Header end
set multiplot layout 3,1;
set title "DataTableSet 2d";
plot for [i=1:10] '-' title info2(1,i) w lp ls ls(i);
0 0
1 1
2 2
3 3
4 4
e
0 0
1 2
2 4
3 6
4 8
e
0 0
1 3
2 6
3 9
4 12
e
0 0
1 4
2 8
3 12
4 16
e
0 0
1 5
2 10
3 15
4 20
e
0 0
1 6
2 12
3 18
4 24
e
0 0
1 7
2 14
3 21
4 28
e
0 0
1 8
2 16
3 24
4 32
e
0 0
1 9
2 18
3 27
4 36
e
0 0
1 10
2 20
3 30
4 40
e
set title "DataTableSet 3d";
plot for [i=1:2] '-' title info2(2,i) w lp ls ls(i);
-2.0 -2.0 8.0
-2.0 -0.5 4.25
-2.0 1.0 5.0
-1.5 -2.0 6.25
-1.5 -0.5 2.5
-1.5 1.0 3.25
-1.0 -2.0 5.0
-1.0 -0.5 1.25
-1.0 1.0 2.0
-0.5 -2.0 4.25
-0.5 -0.5 0.5
-0.5 1.0 1.25
0.0 -2.0 4.0
0.0 -0.5 0.25
0.0 1.0 1.0
0.5 -2.0 4.25
0.5 -0.5 0.5
0.5 1.0 1.25
1.0 -2.0 5.0
1.0 -0.5 1.25
1.0 1.0 2.0
1.5 -2.0 6.25
1.5 -0.5 2.5
1.5 1.0 3.25
2.0 -2.0 8.0
2.0 -0.5 4.25
2.0 1.0 5.0
e
-2.0 -2.0 12.0
-2.0 -1.5 10.25
-2.0 -1.0 9.0
-2.0 -0.5 8.25
-2.0 0.0 8.0
-2.0 0.5 8.25
-2.0 1.0 9.0
-2.0 1.5 10.25
-2.0 2.0 12.0
-1.5 -2.0 10.25
-1.5 -1.5 8.5
-1.5 -1.0 7.25
-1.5 -0.5 6.5
-1.5 0.0 6.25
-1.5 0.5 6.5
-1.5 1.0 7.25
-1.5 1.5 8.5
-1.5 2.0 10.25
-1.0 -2.0 9.0
-1.0 -1.5 7.25
-1.0 -1.0 6.0
-1.0 -0.5 5.25
-1.0 0.0 5.0
-1.0 0.5 5.25
-1.0 1.0 6.0
-1.0 1.5 7.25
-1.0 2.0 9.0
-0.5 -2.0 8.25
-0.5 -1.5 6.5
-0.5 -1.0 5.25
-0.5 -0.5 4.5
-0.5 0.0 4.25
-0.5 0.5 4.5
-0.5 1.0 5.25
-0.5 1.5 6.5
-0.5 2.0 8.25
0.0 -2.0 8.0
0.0 -1.5 6.25
0.0 -1.0 5.0
0.0 -0.5 4.25
0.0 0.0 4.0
0.0 0.5 4.25
0.0 1.0 5.0
0.0 1.5 6.25
0.0 2.0 8.0
0.5 -2.0 8.25
0.5 -1.5 6.5
0.5 -1.0 5.25
0.5 -0.5 4.5
0.5 0.0 4.25
0.5 0.5 4.5
0.5 1.0 5.25
0.5 1.5 6.5
0.5 2.0 8.25
1.0 -2.0 9.0
1.0 -1.5 7.25
1.0 -1.0 6.0
1.0 -0.5 5.25
1.0 0.0 5.0
1.0 0.5 5.25
1.0 1.0 6.0
1.0 1.5 7.25
1.0 2.0 9.0
1.5 -2.0 10.25
1.5 -1.5 8.5
1.5 -1.0 7.25
1.5 -0.5 6.5
1.5 0.0 6.25
1.5 0.5 6.5
1.5 1.0 7.25
1.5 1.5 8.5
1.5 2.0 10.25
2.0 -2.0 12.0
2.0 -1.5 10.25
2.0 -1.0 9.0
2.0 -0.5 8.25
2.0 0.0 8.0
2.0 0.5 8.25
2.0 1.0 9.0
2.0 1.5 10.25
2.0 2.0 12.0
e
set title "DataTableSet 2d";
plot for [i=1:10] '-' title info2(3,i) w lp ls ls(i);
0 0
1 1
2 2
3 3
4 4
e
0 0
1 2
2 4
3 6
4 8
e
0 0
1 3
2 6
3 9
4 12
e
0 0
1 4
2 8
3 12
4 16
e
0 0
1 5
2 10
3 15
4 20
e
0 0
1 6
2 12
3 18
4 24
e
0 0
1 7
2 14
3 21
4 28
e
0 0
1 8
2 16
3 24
4 32
e
0 0
1 9
2 18
3 27
4 36
e
0 0
1 10
2 20
3 30
4 40
e
unset multiplot;
