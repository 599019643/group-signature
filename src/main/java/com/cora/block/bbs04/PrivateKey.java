package com.cora.block.bbs04;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

/**
 *
 * @author maochaowu
 * @date 2023/4/25 19:48
 */
@Data
public class PrivateKey {

    public Group Group;
    public Element xi1;
    public Element xi2;
    public Element gamma;
}
