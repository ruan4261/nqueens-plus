package com.ruan.alg.nqueens;

/**
 * N皇后自定义加强版算法
 * 这个版本减弱了对象概念，减少无用迭代
 * 应该算是使用了回溯算法
 * 速度大大提高，概念更加清晰
 *
 * 计算标准12皇后用时15s 配置:i7 8750H 2.20GHz
 *
 * @author ruan4261
 */
public class NQueensPlusFast {

    //2d打印开关（不打印2D视图可以使运算速度提高很多倍）
    //xy轴大于12后建议关闭此项，可以提速百倍
    static final boolean PRINT = false;
    //皇后数
    static final int QUEENS = 10;
    //X轴长度
    static final int X = 10;
    //Y轴长度
    static final int Y = 10;
    //左上为起点的对角线轴长度（根据XY自动计算）
    static final int L;
    //右上为起点的对角线轴长度（根据XY自动计算）
    static final int R;
    //皇后操作指针
    static int queenPointer;
    //解
    static int result;
    /**
     * 应用数组
     */
    //每个皇后的位置-->外围数组保存皇后-->内围数组为[x坐标,y坐标]
    static final int[][] queenLocal;
    //X轴安全标识数组
    static final boolean[] xSafe;
    //Y轴安全标识数组
    //static final boolean[] ySafe;
    //L轴安全标识数组
    static final boolean[] lSafe;
    //R轴安全标识数组
    static final boolean[] rSafe;

    /**
     * init
     */
    static {
        //指针归零
        queenPointer = 0;
        //初始化对角线轴
        R = L = X + Y - 1;
        //初始化安全标识
        queenLocal = new int[QUEENS][];
        for (int i = 0; i < QUEENS; i++) {
            queenLocal[i] = new int[2];
        }
        xSafe = new boolean[X];
        for (int i = 0; i < X; i++) {
            xSafe[i] = true;
        }
        /*ySafe = new boolean[Y];
        for (int i = 0; i < Y; i++) {
            ySafe[i] = true;
        }*/
        lSafe = new boolean[L];
        for (int i = 0; i < L; i++) {
            lSafe[i] = true;
        }
        rSafe = new boolean[R];
        for (int i = 0; i < R; i++) {
            rSafe[i] = true;
        }
    }

    /**
     * 启动函数
     *
     * @param args
     */
    public static void main(String[] args) {
        long open = System.currentTimeMillis();

        //这里是起始行的控制器
        for (int i = 0; i <= Y - QUEENS; i++) {
            call(i);
            //指针归零
            queenPointer = 0;
        }

        long end = System.currentTimeMillis();

        System.out.println("**************************** 最终结果 ****************************");
        System.out.println("X轴长度:" + X);
        System.out.println("Y轴高度:" + Y);
        System.out.println("皇后数:" + QUEENS);
        System.out.println("共耗时:" + (end - open) + "ms");
        System.out.println("共得出解:" + result + "个");
        System.out.println("**************************** ruan4261 ****************************");
    }

    /**
     * 进入当前行
     * 此函数迭代的为y值
     *
     * @param line 行下标
     */
    static void call(int line) {
        /**
         * X为列
         * Y为行
         */
        //对列迭代
        for (int col = 0; col < X; col++) {
            //判断当前列是否能够放置皇后
            if (xSafe[col] && lSafe[line + col] && rSafe[line + (X - (col + 1))]) {

                //放置皇后
                queenLocal[queenPointer][0] = col;
                queenLocal[queenPointer][1] = line;

                //修改安全标识
                xSafe[col] = lSafe[line + col] = rSafe[line + (X - (col + 1))] = false;

                //判断指针
                if (queenPointer < QUEENS - 1) {
                    //行进至下一行
                    for (int i = line + 1; i < Y; i++) {
                        //当前指针皇后已经放置-->修改指针
                        queenPointer++;
                        //这里是行控制器
                        call(i);
                    }
                } else {
                    result++;
                    if (PRINT) print();
                }

                //清理污染，回溯
                xSafe[col] = lSafe[line + col] = rSafe[line + (X - (col + 1))] = true;
            }

            //这里是列循环的圈，不要阻止其进入下一次循环
        }
        //到这个地方，说明当前行的列已经迭代完成
        //出call函数时，将指针返还
        queenPointer--;
    }

    /**
     * 打印器
     */
    static void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("第");
        sb.append(result);
        sb.append("个解：\n");
        //模拟棋盘
        int[][] chess = new int[Y][X];
        //迭代皇后
        for (int[] intArr : queenLocal) {
            chess[intArr[1]][intArr[0]] = 1;
        }
        //预打印行
        for (int i = 0; i < chess.length; i++) {
            //预打印列
            for (int j = 0; j < chess[i].length; j++) {
                if (chess[i][j] != 1) {
                    sb.append("0 ");
                } else {
                    sb.append("1 ");
                }
            }
            sb.append("\n");
        }
        sb.append("****************************");
        System.out.println(sb.toString());
    }
}
