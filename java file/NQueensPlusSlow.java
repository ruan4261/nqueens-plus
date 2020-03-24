package com.ruan.alg.nqueens;

import java.util.ArrayList;
import java.util.List;

/**
 * N皇后自定义版oop递归算法
 * 您可以完全自定义X轴Y轴长度，以及皇后数量，结果保证正确...但是
 * 算得很慢很慢很慢
 * 至于有多慢呢 - .-!
 *
 * 计算标准12皇后用时???s 配置:i7 8750H 2.20GHz
 * 我愣是等不到它算出来
 * 这个类写的算法根本不能计算N皇后，因为我是个乐色
 *
 * 对于为什么跑的这么慢：
 * 完全使用递归，还带了一屁股的面向对象...该算的不该算的都算了，判断去重，这压根已经不是n皇后了- .-''
 * 谨慎烧掉cpu，再见
 *
 * @author ruan4261
 */
public class NQueensPlusSlow {

    //X轴列数
    final static int X_COL = 12;
    //Y轴行数
    final static int Y_LIN = 12;
    //皇后数量（不得大于X轴或Y轴，否则无解）
    final static int QUEENS = 12;
    //皇后列表
    final static List<Queen> queens;
    //解的次数
    static int result = 0;

    static {
        queens = new ArrayList<Queen>();
        for (int i = 0; i < QUEENS; i++) {
            queens.add(i, new Queen());
        }
    }

    /**
     * 启动函数
     *
     * @param args
     */
    public static void main(String[] args) {
        long open = System.currentTimeMillis();
        call(0);
        long end = System.currentTimeMillis();

        //打印结果
        System.out.println("-------------------- 最终结果 --------------------");
        System.out.println("N皇后自定义版：");
        System.out.println("列数：" + X_COL);
        System.out.println("行数：" + Y_LIN);
        System.out.println("皇后数：" + QUEENS);
        System.out.println("共求得解：" + result);
        System.out.println("共耗时：" + (end - open) + "ms");
        System.out.println("------------------- ruan4261 -------------------");
    }

    /**
     * 运算函数
     *
     * @param index
     */
    static void call(int index) {

        for (int x = 1; x <= X_COL; x++) {
            queens.get(index).setX(x);
            if (!isXBalance(index)) continue;


            for (int y = 1; y <= Y_LIN; y++) {
                queens.get(index).setY(y);
                if (!isAllBalance(index)) continue;


                if (index + 1 < queens.size()) {
                    call(index + 1);
                } else putResult();

            }

        }

        clearAfter(index);
    }

    /**
     * 冒泡排序对比整个列表结果
     */
    static void putResult() {
        for (int i = 0; i < queens.size(); i++) {
            for (int j = i + 1; j < queens.size(); j++) {
                if (queens.get(i).x == queens.get(j).x || queens.get(i).y == queens.get(j).y || queens.get(i).leftTopToRightBottom == queens.get(j).leftTopToRightBottom || queens.get(i).rightTopToLeftBottom == queens.get(j).rightTopToLeftBottom) {
                    return;
                }
            }
        }
        result++;
        /*System.out.println("------------------------------解:" + result + "---------------------------------");
        for (Queen queen : queens) {
            System.out.println(queen.toString());
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("\n");*/
    }

    /**
     * 判断四项平衡
     *
     * @param index
     * @return
     */
    static boolean isAllBalance(int index) {
        if (isXBalance(index) && isYBalance(index) && isLBalance(index) && isRBalance(index)) return true;
        else return false;
    }

    /**
     * 判断X轴平衡
     *
     * @param index
     * @return
     */
    static boolean isXBalance(int index) {
        List<Integer> x = new ArrayList<Integer>();
        for (int i = 0; i <= index; i++) {
            if (x.contains(queens.get(i).x)) {
                return false;
            } else {
                x.add(queens.get(i).x);
            }
        }
        return true;
    }

    /**
     * 判断Y轴平衡
     *
     * @param index
     * @return
     */
    static boolean isYBalance(int index) {
        List<Integer> y = new ArrayList<Integer>();
        int currentMax = 0;
        for (int i = 0; i <= index; i++) {

            if (i > 0) {
                if (queens.get(i).y <= currentMax) return false;
            }

            currentMax = queens.get(i).y;

            if (y.contains(queens.get(i).y)) {
                return false;
            } else {
                y.add(queens.get(i).y);
            }
        }
        return true;
    }

    /**
     * 判断L斜轴平衡
     *
     * @param index
     * @return
     */
    static boolean isLBalance(int index) {
        List<Integer> l = new ArrayList<Integer>();
        for (int i = 0; i <= index; i++) {
            if (l.contains(queens.get(i).leftTopToRightBottom)) {
                return false;
            } else {
                l.add(queens.get(i).leftTopToRightBottom);
            }
        }
        return true;
    }

    /**
     * 判断R斜轴平衡
     *
     * @param index
     * @return
     */
    static boolean isRBalance(int index) {
        List<Integer> r = new ArrayList<Integer>();
        for (int i = 0; i <= index; i++) {
            if (r.contains(queens.get(i).rightTopToLeftBottom)) {
                return false;
            } else {
                r.add(queens.get(i).rightTopToLeftBottom);
            }
        }
        return true;
    }

    /**
     * 重置指定下标开始的元素
     *
     * @param index
     */
    static void clearAfter(int index) {
        for (; index < queens.size(); index++) {
            queens.set(index, new Queen());
        }
    }

    /**
     * 皇后对象
     */
    static class Queen {
        int x = 1;
        int y = 1;
        int leftTopToRightBottom = 1;
        int rightTopToLeftBottom = 1;

        void setX(int x) {
            this.x = x;
            this.leftTopToRightBottom = this.x + this.y - 1;
            this.rightTopToLeftBottom = this.x + (Y_LIN - this.y);
        }


        void setY(int y) {
            this.y = y;
            this.leftTopToRightBottom = this.x + this.y - 1;
            this.rightTopToLeftBottom = this.x + (Y_LIN - this.y);
        }

        @Override
        public String toString() {
            return "[{x=" + x + "},{y=" + y + "},{l=" + leftTopToRightBottom + "},{r=" + rightTopToLeftBottom + "}]";
        }

    }
}
