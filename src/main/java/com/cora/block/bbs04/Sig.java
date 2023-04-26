package com.cora.block.bbs04;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

/**
 *
 * @author maochaowu
 * @date 2023/4/26 9:25
 */
@Data
public class Sig {
    public String m;
    public Element t1;
    public Element t2;
    public Element t3;
    public Element c;
    public Element salpha;
    public Element sbeta;
    public Element sa;
    public Element sdelta1;
    public Element sdelta2;
}
