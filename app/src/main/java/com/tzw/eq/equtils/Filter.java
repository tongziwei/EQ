package com.tzw.eq.equtils;

import android.util.Log;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Filter {
      /*function [coeff_b,coeff_a] = GetLowShelfEQ(f,Q,dBgain,fs)
            %%% shelf EQ coeff
            w0      = 2*pi*f/fs;
            alpha   = sin(w0)/(2*Q);
            A       =   10^(dBgain/40);
            %%% lowShelf: H(s) = A * (s^2 + (sqrt(A)/Q)*s + A)/(A*s^2 + (sqrt(A)/Q)*s + 1)
            b0      =    A*( (A+1) - (A-1)*cos(w0) + 2*sqrt(A)*alpha );
            b1      =  2*A*( (A-1) - (A+1)*cos(w0)                   );
            b2      =    A*( (A+1) - (A-1)*cos(w0) - 2*sqrt(A)*alpha );
            a0      =        (A+1) + (A-1)*cos(w0) + 2*sqrt(A)*alpha;
            a1      =   -2*( (A-1) + (A+1)*cos(w0)                   );
            a2      =        (A+1) + (A-1)*cos(w0) - 2*sqrt(A)*alpha;
            %%%
            coeff_b=[b0,b1,b2]';
            coeff_a=[a0,a1,a2]';
        end*/
    public static Coeff getLowShelfEQ(double f, double Q,double dBgain,int fs){
        //shelf EQ coeff
        double  w0 = 2*Math.PI*f/fs;
        double alpha = sin(w0)/(2*Q);
        double A = pow(10,dBgain/40);      //10^(dBgain/40);
       // lowShelf: H(s) = A * (s^2 + (sqrt(A)/Q)*s + A)/(A*s^2 + (sqrt(A)/Q)*s + 1)
        double b0 =    A*( (A+1) - (A-1)*cos(w0) + 2*sqrt(A)*alpha );
        double b1 =  2*A*( (A-1) - (A+1)*cos(w0));
        double b2 =    A*( (A+1) - (A-1)*cos(w0) - 2*sqrt(A)*alpha );
        double a0 =  (A+1) + (A-1)*cos(w0) + 2*sqrt(A)*alpha;
        double a1 =   -2*( (A-1) + (A+1)*cos(w0));
        double a2 =   (A+1) + (A-1)*cos(w0) - 2*sqrt(A)*alpha;

        Coeff coeff = new Coeff();
        coeff.setB0(b0);
        coeff.setB1(b1);
        coeff.setB2(b2);
        coeff.setA0(a0);
        coeff.setA1(a1);
        coeff.setA2(a2);
        Log.d("tzw", "GetLowShelfEQ: "+coeff.toStr());
        return coeff;
    }


    /*
    function [coeff_b,coeff_a] = GetHighShelfEQ(f,Q,dBgain,fs)
        %%% highshelf EQ coeff
        w0      = 2*pi*f/fs;
        alpha   = sin(w0)/(2*Q);
        A       =   10^(dBgain/40);

        %%%highShelf: H(s) = A * (A*s^2 + (sqrt(A)/Q)*s + 1)/(s^2 + (sqrt(A)/Q)*s + A)
        b0 =    A*( (A+1) + (A-1)*cos(w0) + 2*sqrt(A)*alpha );
        b1 = -2*A*( (A-1) + (A+1)*cos(w0)                   );
        b2 =    A*( (A+1) + (A-1)*cos(w0) - 2*sqrt(A)*alpha );
        a0 =        (A+1) - (A-1)*cos(w0) + 2*sqrt(A)*alpha;
        a1 =    2*( (A-1) - (A+1)*cos(w0)                   );
        a2 =        (A+1) - (A-1)*cos(w0) - 2*sqrt(A)*alpha;

        %%%
        coeff_b=[b0,b1,b2]';
        coeff_a=[a0,a1,a2]';
    end
    * */
    public static Coeff getHighShelfEQ(double f, double Q,double dBgain,int fs){
        //highshelf EQ coeff
        double  w0 = 2*Math.PI*f/fs;
        double alpha = sin(w0)/(2*Q);
        double A = pow(10,dBgain/40);

         //highShelf: H(s) = A * (A*s^2 + (sqrt(A)/Q)*s + 1)/(s^2 + (sqrt(A)/Q)*s + A)
        double b0 =    A*( (A+1) + (A-1)*cos(w0) + 2*sqrt(A)*alpha );
        double b1 = -2*A*( (A-1) + (A+1)*cos(w0)                   );
        double b2 =    A*( (A+1) + (A-1)*cos(w0) - 2*sqrt(A)*alpha );
        double a0 =        (A+1) - (A-1)*cos(w0) + 2*sqrt(A)*alpha;
        double a1 =    2*( (A-1) - (A+1)*cos(w0)                   );
        double a2 =        (A+1) - (A-1)*cos(w0) - 2*sqrt(A)*alpha;
        Coeff coeff = new Coeff();
        coeff.setB0(b0);
        coeff.setB1(b1);
        coeff.setB2(b2);
        coeff.setA0(a0);
        coeff.setA1(a1);
        coeff.setA2(a2);
        Log.d("tzw", "GetHighShelfEQ: "+coeff.toStr());
        return coeff;
    }


    /*
    * function [coeff_b,coeff_a] = GetPeakEQ(f,Q,dBgain,fs)
        %%% peak EQ coeff
        w0 = 2*pi*f/fs;
        alpha = sin(w0)/(2*Q);
        A  =   10^(dBgain/40);
        b0 =   1 + alpha*A;
        b1 =  -2*cos(w0);
        b2 =   1 - alpha*A;
        a0 =   1 + alpha/A;
        a1 =  -2*cos(w0);
        a2 =   1 - alpha/A;
        %%%
        coeff_b=[b0,b1,b2]';
        coeff_a=[a0,a1,a2]';
    end
* */
    public static Coeff getPeakEQ(double f, double Q,double dBgain,int fs){
        //peak EQ coeff
        double  w0 = 2*Math.PI*f/fs;
        double alpha = sin(w0)/(2*Q);
        double A = pow(10,dBgain/40);

        double b0 =   1 + alpha*A;
        double b1 =  -2*cos(w0);
        double b2 =   1 - alpha*A;
        double a0 =   1 + alpha/A;
        double a1 =  -2*cos(w0);
        double a2 =   1 - alpha/A;
        Coeff coeff = new Coeff();
        coeff.setB0(b0);
        coeff.setB1(b1);
        coeff.setB2(b2);
        coeff.setA0(a0);
        coeff.setA1(a1);
        coeff.setA2(a2);
        Log.d("tzw", "GetPeakEQ: "+coeff.toStr());
        return coeff;
    }



}
