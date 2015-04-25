/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Email;




/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "professor")
@Indexed
@AnalyzerDef(name = "customanalyzer",
  tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
  filters = {
    @TokenFilterDef(factory = LowerCaseFilterFactory.class),
    @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
      @Parameter(name = "language", value = "Portuguese")
    }),
    @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
    @TokenFilterDef(factory = StopFilterFactory.class, params = {
      @Parameter(name = "words", value = "words.properties")
    })
    
  })
@NamedQuery(name = "Professor.getAll"
        , query = "select distinct p from Professor p left join fetch p.materias inner"
                + " join fetch p.endereco order by p.id asc")
public class Professor implements Serializable
{
    private static final Long serialVersionUID = 1L;
    
    public static final String GET_ALL = "Professor.getAll";
    
    @Id
    @GeneratedValue
    @Column(name = "professor_ID")
    private Long id;
    
    @Field
    @Analyzer(definition = "customanalyzer")
    @NotNull
    @Size(min = 2, max = 20, message = "O Nome deve ter entre 2 e 20 caracteres!")
    @Column(name = "professor_NOME")
    private String nome;
    
    @Field
    @Analyzer(definition = "customanalyzer")
    @NotNull
    @Size(min = 2, max = 40, message = "O Sobrenome deve ter entre 2 e 40 carac"
            + "teres")
    @Column(name = "professor_SOBRENOME")
    private String sobrenome;
    
    @Field
    @Analyzer(definition = "customanalyzer")
    @NotNull
    @Size(max = 255, message = "Não deve possuir mais que {max} caracteres!")
    @Column(name = "professor_FORMACAO", length = 255)
    private String formacao;
    

    @Email(message = "Digite um e-mail válido!")
    @Column(name = "professor_EMAIL", length = 75, nullable = true)
    private String email;
    
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_ID_professor")
    @Fetch(FetchMode.JOIN)
    private Endereco endereco;
    
//    @OneToMany(mappedBy = "professor")
//    @ElementCollection
    @Transient
    private List<Telefone> telefones;
   
    
    @IndexedEmbedded
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<Materia> materias;

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nome);
        hash = 37 * hash + Objects.hashCode(this.sobrenome);
        hash = 37 * hash + Objects.hashCode(this.formacao);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.endereco);
        hash = 37 * hash + Objects.hashCode(this.telefones);
        hash = 37 * hash + Objects.hashCode(this.materias);
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
        final Professor other = (Professor) obj;
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
        if (!Objects.equals(this.formacao, other.formacao))
        {
            return false;
        }
        if (!Objects.equals(this.email, other.email))
        {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco))
        {
            return false;
        }
        if (!Objects.equals(this.telefones, other.telefones))
        {
            return false;
        }
        if (!Objects.equals(this.materias, other.materias))
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
     * @return the formacao
     */
    public String getFormacao()
    {
        return formacao;
    }

    /**
     * @param formacao the formacao to set
     */
    public void setFormacao(String formacao)
    {
        this.formacao = formacao;
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
     * @return the materias
     */
    public List<Materia> getMaterias()
    {
        return materias;
    }

    /**
     * @param materias the materias to set
     */
    public void setMaterias(List<Materia> materias)
    {
        this.materias = materias;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    
}
