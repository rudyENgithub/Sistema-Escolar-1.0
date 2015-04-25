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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "serie")
public class Serie implements Serializable
{
    private static final Long serialVersionUID = 1L;
    
    public static final String GET_ALL = "Serie.getAll";
    
    @Id
    @GeneratedValue
    @Column(name = "serie_ID")
    private Long id;
    
    @NotNull
    @Size(max = 30, message = "Deve ter menos que #{max} caracteres!")
    @Column(name = "serie_NOME")
    private String nome;
    
    @OneToMany(mappedBy = "serie", cascade = CascadeType.REMOVE, 
            orphanRemoval = true)
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<Turma> turmas;
    
    @OneToMany(mappedBy = "serie", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<Materia> materias;
    
    @OneToMany(mappedBy = "serie")
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<Aluno> alunos;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.nome);
        hash = 23 * hash + Objects.hashCode(this.turmas);
        hash = 23 * hash + Objects.hashCode(this.materias);
        hash = 23 * hash + Objects.hashCode(this.getAlunos());
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
        final Serie other = (Serie) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome))
        {
            return false;
        }
        if (!Objects.equals(this.turmas, other.turmas))
        {
            return false;
        }
        if (!Objects.equals(this.alunos, other.alunos))
        {
            return false;
        }
        if (!Objects.equals(this.materias, other.materias))
        {
            return false;
        }
        return true;
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
     * @return the turmas
     */
    public List<Turma> getTurmas()
    {
        return turmas;
    }

    /**
     * @param turmas the turmas to set
     */
    public void setTurmas(List<Turma> turmas)
    {
        this.turmas = turmas;
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
     * @return the alunos
     */
    public List<Aluno> getAlunos()
    {
        return alunos;
    }

    /**
     * @param alunos the alunos to set
     */
    public void setAlunos(List<Aluno> alunos)
    {
        this.alunos = alunos;
    }
    
    
}
