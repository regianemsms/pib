package br.org.piblimeira.domain;

import javax.persistence.*;

/**
 ** @author Regiane
 **/
@Entity
@Table(name = "tb_tipo_membro")
public class TipoMembro {

    @Id
    @Column(name = "ID_TP_MEMBRO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NM_TP_MEMBRO")
    private String tpMembro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTpMembro() {
        return tpMembro;
    }

    public void setTpMembro(String tpMembro) {
        this.tpMembro = tpMembro;
    }

}
