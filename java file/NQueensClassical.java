package com.ruan.alg.nqueens;

/**
 * 修改自xiaoxinye
 * 原博客https://www.iteye.com/blog/xiaoxinye-1202245
 * 此类算法解析：
 * 算的好G2快
 * 非静态用时比静态用时平均快了百分之二（不知道为什么）
 *
 * 计算标准12皇后用时70ms 配置:i7 8750H 2.20GHz
 *
 * @updateBy ruan4261
 */
public class NQueensClassical {

    //0-->打印会非常影响计时，故默认关闭
    //1-->打印行列信息
    //2-->打印2D视图
    static final int print = 0;
    //皇后数(请设置这里)
    static final int n = 12;
    //解
    static int result;
    //每个皇后的位置-->下标为行 / 值为列
    static int[] queenCol;
    //列安全标志
    static boolean[] col;
    //对角线安全标志
    static boolean[] diagonal;
    //反对角线安全标志
    static boolean[] undiagonal;

    /**
     * init
     */
    static {
        queenCol = new int[n];
        col = new boolean[n];
        diagonal = new boolean[2 * n - 1];
        undiagonal = new boolean[2 * n - 1];
        for (int i = 0; i < n; i++)
            //置所有列为安全
            col[i] = true;
        for (int t = 0; t < (2 * n - 1); t++)
            //置所有对角线为安全
            diagonal[t] = undiagonal[t] = true;
    }

    /**
     * 启动函数
     *
     * @param args
     */
    public static void main(String[] args) {
        long open = System.currentTimeMillis();
        solve(0);
        System.out.println("\n**********" + n + "皇后共耗时:" + (System.currentTimeMillis() - open) + "ms**********");
        System.out.println("************得出解：" + result + "************");
    }

    /**
     * 行迭代
     *
     * @param i
     */
    static void solve(int i) {
        //对行中列进行迭代
        for (int j = 0; j < n; j++) {

            //判断当前所在列及斜轴是否允许摆放皇后
            if (col[j] && diagonal[i - j + n - 1] && undiagonal[i + j]) {

                queenCol[i] = j;
                //修改当前摆放点对应的列及斜轴的安全标识
                col[j] = false;
                diagonal[i - j + n - 1] = false;
                undiagonal[i + j] = false;

                //判断结果
                if (i < n - 1) {
                    //还有皇后没有放置
                    solve(i + 1);
                } else {
                    //已经放置完全部皇后
                    //得出了一个解
                    result++;

                    //打印一次解
                    if (print == 1) {
                        System.out.println("皇后摆放第" + result + "种方案:");
                        System.out.print("行分别为");
                        for (int k = 0; k < n; k++)
                            System.out.print(k + " ");
                        System.out.println();
                        System.out.print("列分别为");
                        for (int k = 0; k < n; k++)
                            System.out.print(queenCol[k] + " ");
                        System.out.println();
                    } else if (print == 2) {
                        System.out.println("皇后摆放第" + result + "种方案:");
                        for (int k = 0; k < n; k++) {//每一行的循环

                            for (int l = 0; l < n; l++) {//每一列的循环
                                if (l == queenCol[k]) {
                                    System.out.print("1 ");
                                } else System.out.print("0 ");
                            }
                            System.out.println();
                        }
                    }
                }

                //将本次循环使用的安全标识恢复为true，回溯到上一次
                col[j] = true;
                diagonal[i - j + n - 1] = true;
                undiagonal[i + j] = true;
            }
        }
    }

}
