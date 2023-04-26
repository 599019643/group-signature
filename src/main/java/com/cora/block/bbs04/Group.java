package com.cora.block.bbs04;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.Data;

/**
 * Group
 * @author maochaowu
 * @date 2023/4/25 19:49
 */
@Data
public class Group {

    public Element g1;
    public Element h;
    public Element u;
    public Element v;
    public Element g2;
    public Element w;
    public Element ehw;
    public Element ehg2;
    public Pairing pairing;
}
