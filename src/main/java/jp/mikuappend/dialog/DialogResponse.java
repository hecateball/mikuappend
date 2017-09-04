package jp.mikuappend.dialog;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hecateball
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class DialogResponse implements Serializable {

    @XmlElement(name = "utt")
    private String utt;
    @XmlElement(name = "yomi")
    private String yomi;
    @XmlElement(name = "mode")
    private String mode;
    @XmlElement(name = "da")
    private String da;
    @XmlElement(name = "context")
    private String context;

    public String getUtt() {
        return utt;
    }

    public String getYomi() {
        return yomi;
    }

    public String getMode() {
        return mode;
    }

    public String getDa() {
        return da;
    }

    public String getContext() {
        return context;
    }

}
