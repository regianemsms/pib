package br.org.piblimeira.domain;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Entity
@Table(name="tb_pessoa")
public class Pessoa {
	

	@Id
	@Column(name = "ID_PESSOA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DT_NASCIMENTO")
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;
	
	@Column(name = "SEXO", columnDefinition = "char(1)")
	private String sexo;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "STATUS", columnDefinition = "char(1)")
	private String status;
	
	@Column(name = "ESTADO_CIVIL", columnDefinition = "char(1)")
	private String estadoCivil;
	
	@Column(name = "MOTIVO_DESLIGAMENTO")
	private String motivo_desligamento;
	
	@Column(name = "OBSERVACAO")
	private String obs;
	
	@Column(name = "TELEFONE_FIXO")
	private String fixo;
	
	@Column(name = "CELULAR")
	private String celular;
	
	@Column(name = "DT_BATISMO")
	@Temporal(TemporalType.DATE)
	private Date dtBatismo;
	
	@Column(name = "DT_RECEPCAO")
	@Temporal(TemporalType.DATE)
	private Date dtRecepcao;
	
	@Column(name="ID_USUARIO")
	private Long usuario;
	
	@ManyToOne
	@JoinColumn(name="ID_TP_RECEPCAO")
	private TipoRecepcao tipoRecepcao;
	
	@ManyToOne
	@JoinColumn(name="ID_TP_MEMBRO")
	private TipoMembro tipoMembro;
	
	@Transient
	private Endereco endereco;
	
	@Transient
	private Integer mesNascimento;
	
	public Pessoa() {
	}
	
	public String retornarPrimeiroNome() {
		if(StringUtils.isNotBlank(getNome())) {
			String primeiroNome[] = getNome().split(" ");
			return primeiroNome[0];
		}
		return "";
	}
	
	public Pessoa(Integer mesNascimento) {
		this.mesNascimento = mesNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMotivo_desligamento() {
		return motivo_desligamento;
	}

	public void setMotivo_desligamento(String motivo_desligamento) {
		this.motivo_desligamento = motivo_desligamento;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getFixo() {
		return fixo;
	}

	public void setFixo(String fixo) {
		this.fixo = fixo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getDtBatismo() {
		return dtBatismo;
	}

	public void setDtBatismo(Date dtBatismo) {
		this.dtBatismo = dtBatismo;
	}

	public Date getDtRecepcao() {
		return dtRecepcao;
	}

	public void setDtRecepcao(Date dtRecepcao) {
		this.dtRecepcao = dtRecepcao;
	}

	public TipoRecepcao getTipoRecepcao() {
		return tipoRecepcao;
	}

	public void setTipoRecepcao(TipoRecepcao tipoRecepcao) {
		this.tipoRecepcao = tipoRecepcao;
	}

	public TipoMembro getTipoMembro() {
		return tipoMembro;
	}

	public void setTipoMembro(TipoMembro tipoMembro) {
		this.tipoMembro = tipoMembro;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Integer getMesNascimento() {
		return mesNascimento;
	}

	public void setMesNascimento(Integer mesNascimento) {
		this.mesNascimento = mesNascimento;
	}

}
