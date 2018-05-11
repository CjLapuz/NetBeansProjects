package pkg141_mps;

import java.util.*;
import java.util.ArrayList.*;
import java.lang.*;
import java.io.BufferedReader;

public class Sample {
  public static void main(String args[]){
  }

  public boolean checkInitial(char charArr[], int i) {
    return ((charArr [i] >= 'a' && charArr[i] <= 'z') || charArr[i] == '_' );
  }

  public boolean checkInitialVar(char charArr[], int i) {
    return ((charArr [i] >= 'a' && charArr[i] <= 'z') || charArr[i] == '_'  || charArr[i] == ')' || charArr[i] == ',');
  }

  public boolean checkChar(char charArr[], int i) {
    return ((charArr [i] >= 'a' && charArr[i] <= 'z') || (charArr [i] >= 'A' && charArr[i] <= 'Z') || (charArr[i] >= '0' && charArr[i] <= '9') || charArr[i] == 39);
  }

  public boolean checkNum(char charArr[], int j) {
    return ((charArr[j] >= '0' && charArr[j] <= '9') || (charArr[j] == '+' || charArr[j] == '-' || charArr[j] == '*' || charArr[j] == '/') || charArr[j] == '(' || charArr[j] == ')' );
  }

  public boolean checkNumFloat(char charArr[], int j) {
    return ((charArr[j] >= '0' && charArr[j] <= '9') || charArr[j] == '+' || charArr[j] == '-' || charArr[j] == '*' || charArr[j] == '/' || charArr[j] == '.');
  }

  public boolean checkNext(char charArr[], int i) {
    return ((charArr [i] >= 'a' && charArr[i] <= 'z') || charArr[i] == '_' || (charArr[i]>= 'A' && charArr[i] <= 'Z') || (charArr[i] >= '0' && charArr[i] <= '9'));
  }

  public boolean checkNext(char nxt) {
    return (nxt >= ' ' && nxt <= '~');
  }

  public boolean isValidVarDec(String line) {
    boolean i, f, c, d;
    i = isValidIntDec(line);
    f = isValidFloatDec(line);
    d = isValidDoubleDec(line);
    c = isValidCharDec(line);
    return i || f || d || c;
  }

  public boolean isValidIntDec(String line) {
    String intStab = line;
    intStab = line.replace("int ", "");
    char charArr[] = intStab.toCharArray();
    StringBuffer sbint = new StringBuffer();

    for(int i = 0; i < charArr.length; i++) {
      if((charArr[i] != ' ') && (charArr[i] != '\t')) {
        sbint.append(charArr[i]);
      }
    }

    intStab = sbint.toString();
    charArr = intStab.toCharArray();
    boolean check = true;

    if(charArr[charArr.length-1] == ';') {
      for(int i = 0; i < charArr.length - 1 && check == true; i++) {
        if(i == 0) {
          check = checkInitial(charArr,i);
        }
        else if(charArr [i] == ',') {
          check = checkInitial(charArr, i + 1);
        } else if(charArr[i] == '=') {
          int j;
          for(j = i + 1; j < charArr.length - 1 && check == true ; j++) {
            check = checkNum(charArr, j);
          }
          i = j;
        } else {
          check = checkNext(charArr, i);
        }
      }
      return check;
    } else {
      return false;
    }
  }


  public boolean isValidFloatDec(String line) {

    String floatStab = line;
    floatStab = line.replace("float ","");
    char floatArr[] = floatStab.toCharArray();
    StringBuffer sbflt = new StringBuffer();

    for(int i = 0; i < floatArr.length; i++) {
      if((floatArr[i] != ' ') && (floatArr[i] != '\t')) {
        sbflt.append(floatArr[i]);
      }
    }

    floatStab = sbflt.toString();
    floatArr = floatStab.toCharArray();
    boolean flcheck = true;
    int fltcount = 0;

    if(floatArr[floatArr.length - 1] == ';' && flcheck == true) {
      for(int i = 0; i < floatArr.length- 1 && flcheck == true; i++) {
        if(i == 0) {
          flcheck = checkInitial(floatArr, i);
        } else if(floatArr[i] == ',') {
          checkInitial(floatArr, i + 1);
        } else if(floatArr[i] == '=') {
            int j;
          for(j = i + 1; j < floatArr.length - 1 && flcheck == true; j++){
            flcheck = checkNumFloat(floatArr, j);

            if(floatArr[j] == '.') {
              fltcount++;
            }
          }
          i = j;
        } else {
          flcheck = checkNext(floatArr, i);
        }
      }

      if(fltcount > 1) {
        return false;
      } else {
        return flcheck;
        }
      }
      return false;
    }

  public boolean isValidDoubleDec(String line) {

      String doubleStab = line;
      doubleStab = line.replace("double ","");

      char doubleArr[] = doubleStab.toCharArray();
      StringBuffer sbdbl = new StringBuffer();

      for(int i = 0; i < doubleArr.length; i++) {
          if((doubleArr[i] != ' ') && (doubleArr[i] != '\t')) {
              sbdbl.append(doubleArr[i]);
          }
      }

      doubleStab = sbdbl.toString();
      doubleArr = doubleStab.toCharArray();
      boolean dblcheck = true;
      int dblcount = 0;

      if(doubleArr[doubleArr.length - 1] == ';' && dblcheck == true) {
          for(int i = 0; i < doubleArr.length - 1 && dblcheck == true; i++) {
              if(i == 0) {
                  dblcheck = checkInitial(doubleArr, i);
              } else if(doubleArr[i] == ',') {
                  checkInitial(doubleArr, i + 1);
              } else if(doubleArr[i] == '=') {
                  int j = i + 1;
                  for(; j < doubleArr.length - 1 && dblcheck == true; j++) {
                    dblcheck = checkNumFloat(doubleArr, j);
                    if(doubleArr[j] == '.') {
                      dblcount++;
                    }
                  }
                  i = j;
                } else {
                  dblcheck = checkNext(doubleArr, i);
                }
              }
              return dblcheck;
          } else {
            return false;
          }
        }


  public boolean isValidCharDec(String line) {

    String charStab = line;
    charStab = line.replace("char ","");

    char charArr[] = charStab.toCharArray();
    StringBuffer sbchr = new StringBuffer();

    for(int i = 0; i < charArr.length; i++) {
      if((charArr[i] != ' ') && (charArr[i] != '\t')) {
        sbchr.append(charArr[i]);
      }
    }

    charStab = sbchr.toString();
    charArr = charStab.toCharArray();

    boolean chrcheck = true;
    boolean nxt = false;
    int chrcount = 0;
    int chrex = 0;

    if(charArr[charArr.length - 1] == ';' && chrcheck == true) {
      for(int i = 0; i < charArr.length - 1 && chrcheck == true; i++) {
        if(i == 0) {
          chrcheck = checkInitial(charArr, i);
        } else if( charArr[i] == ',') {
          checkInitial(charArr, i + 1);
        } else if (charArr[i] == '='){
          int j = i + 1;
          for(; j < charArr.length - 1; j++) {
            chrcheck = checkChar(charArr, j);
            if(charArr[j] == 39) {
              chrcount++;
              j++;
              if(charArr[j] >= '0' && charArr[j] <= '9') {
                nxt = checkNext(charArr[j + 1]);
                if(nxt == true) {
                  chrex++;
                }
              } else if(charArr[j] >= 'A' && charArr[j] <= 'Z'){
                nxt = checkNext(charArr[j + 1]);
                if(nxt == true) {
                  chrex++;
                }
              } else if(charArr[j] >= 'a' && charArr[j] <= 'z') {
                nxt = checkNext(charArr[j+1]);
                if(nxt == true) {
                  chrex++;
                }
              }
            }
          }
          i = j;
        } else {
          chrcheck = checkNext(charArr, i);
        }
      }

      if(chrcheck == false || chrcount > 2 || chrex != 0) {
        return false;
      } else {
        return chrcheck;
      }
    } else {
      return false;
    }
  }
}
