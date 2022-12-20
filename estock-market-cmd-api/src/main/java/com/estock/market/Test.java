package com.estock.market;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        BigDecimal bg1, bg2;

        bg1 = new BigDecimal("100000000");
        bg2 = new BigDecimal("20");
        int res;

        res = bg2.compareTo(bg1);
        String str1 = "Both values are equal ";
        String str2 = "First Value is greater ";
        String str3 = "Second value is greater";
        if( res == 0 )
            System.out.println( str1 );
        else if( res == 1 )
            System.out.println( str2 );
        else if( res == -1 )
            System.out.println( str3 );
    }

}
