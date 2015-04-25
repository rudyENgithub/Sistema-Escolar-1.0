/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "aluno")
@Indexed
@NamedQueries({
    @NamedQuery(name = "Aluno.getAll",
        query = "SELECT a FROM Aluno a INNER JOIN FETCH a.endereco LEFT JOIN "
                + "FETCH a.turma ORDER BY a.id ASC"),
    @NamedQuery(name = "Aluno.getAllBySerieWithoutTurma",
            query = "SELECT a FROM Aluno a LEFT JOIN FETCH a.turma LEFT JOIN "
                    + "FETCH a.serie WHERE a.serie = :serie AND a.turma IS NULL "
                    + "ORDER BY a.id ASC")
})

public class Aluno implements Serializable
{
    private static final Long serialVersionUID = 1L;
    
    public static final String GET_ALL = "Aluno.getAll";
    public static final String GET_ALL_BY_SERIE_WITHOUT_TURMA = 
            "Aluno.getAllBySerieWithoutTurma";
    
    @Id
    @GeneratedValue
    @Column(name = "aluno_ID")
    private Long id;
    
    @Field
    @Analyzer(definition = "customanalyzer")
    @NotNull
    @Size(min = 2, max = 20, message = "O Nome deve ter entre 2 e 20 caracteres!")
    @Column(name = "aluno_NOME")
    private String nome;
    
    @Field
    @Analyzer(definition = "customanalyzer")
    @NotNull
    @Size(min = 2, max = 40, message = "O Sobrenome deve ter entre 2 e 40 carac"
            + "teres")
    @Column(name = "aluno_SOBRENOME")
    private String sobrenome;
    
    @Field
    @Analyzer(definition = "customanalyzer")
    @NotNull
    @Size(max = 12, message = "O RG pode ter no máximo 10 caracteres!")
    @Column(name = "aluno_RG")
    private String rg;
    
    @NotNull
    @Past
    @Column(name = "aluno_DATANASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, 
            fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_ID_aluno")
    @Fetch(FetchMode.JOIN)
    private Endereco endereco;
    
    @ManyToOne
    @JoinColumn(name = "turma_ID_aluno")
    private Turma turma;
    
    @ManyToOne
    @JoinColumn(name = "serie_ID_aluno")
    private Serie serie;
    
//    @OneToMany(mappedBy = "aluno")
//    @ElementCollection
    @Transient
    private List<Telefone> telefones;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.sobrenome);
        hash = 97 * hash + Objects.hashCode(this.rg);
        hash = 97 * hash + Objects.hashCode(this.dataNascimento);
        hash = 97 * hash + Objects.hashCode(this.endereco);
        hash = 97 * hash + Objects.hashCode(this.turma);
        hash = 97 * hash + Objects.hashCode(this.serie);
        hash = 97 * hash + Objects.hashCode(this.telefones);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Aluno other = (Aluno) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome))
        {
            return false;
        }
        if (!Objects.equals(this.sobrenome, other.sobrenome))
        {
            return false;
        }
        if (!Objects.equals(this.rg, other.rg))
        {
            return false;
        }
        if (!Objects.equals(this.dataNascimento, other.dataNascimento))
        {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco))
        {
            return false;
        }
        if (!Objects.equals(this.turma, other.turma))
        {
            return false;
        }
        if (!Objects.equals(this.serie, other.serie))
        {
            return false;
        }
        if (!Objects.equals(this.telefones, other.telefones))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return this.nome + " " + this.sobrenome;
    }
    
    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * @return the sobrenome
     */
    public String getSobrenome()
    {
        return sobrenome;
    }

    /**
     * @param sobrenome the sobrenome to set
     */
    public void setSobrenome(String sobrenome)
    {
        this.sobrenome = sobrenome;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco()
    {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco)
    {
        this.endereco = endereco;
    }

    /**
     * @return the turma
     */
    public Turma getTurma()
    {
        return turma;
    }

    /**
     * @param turma the turma to set
     */
    public void setTurma(Turma turma)
    {
        this.turma = turma;
    }

    /**
     * @return the telefones
     */
    public List<Telefone> getTelefones()
    {
        return telefones;
    }

    /**
     * @param telefones the telefones to set
     */
    public void setTelefones(List<Telefone> telefones)
    {
        this.telefones = telefones;
    }

    /**
     * @return the serie
     */
    public Serie getSerie()
    {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(Serie serie)
    {
        this.serie = serie;
    }

    /**
     * @return the rg
     */
    public String getRg()
    {
        return rg;
    }

    /**
     * @param rg the rg to set
     */
    public void setRg(String rg)
    {
        this.rg = rg;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento()
    {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }
    
    
}
