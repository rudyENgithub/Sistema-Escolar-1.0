/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "evento")
public class Evento implements Serializable
{
    private static final Long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    private String titulo;
    private String descricao;
    @Column(name="start_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    @Column(name="final_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;
    private boolean allDay;

    
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.titulo);
        hash = 29 * hash + Objects.hashCode(this.descricao);
        hash = 29 * hash + Objects.hashCode(this.dataInicial);
        hash = 29 * hash + Objects.hashCode(this.dataFinal);
        hash = 29 * hash + (this.allDay ? 1 : 0);
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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo))
        {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao))
        {
            return false;
        }
        if (!Objects.equals(this.dataInicial, other.dataInicial))
        {
            return false;
        }
        if (!Objects.equals(this.dataFinal, other.dataFinal))
        {
            return false;
        }
        if (this.allDay != other.allDay)
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
     * @return the titulo
     */
    public String getTitulo()
    {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao()
    {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    /**
     * @return the dataInicial
     */
    public Date getDataInicial()
    {
        return dataInicial;
    }

    /**
     * @param dataInicial the dataInicial to set
     */
    public void setDataInicial(Date dataInicial)
    {
        this.dataInicial = dataInicial;
    }

    /**
     * @return the dataFinal
     */
    public Date getDataFinal()
    {
        return dataFinal;
    }

    /**
     * @param dataFinal the dataFinal to set
     */
    public void setDataFinal(Date dataFinal)
    {
        this.dataFinal = dataFinal;
    }

    /**
     * @return the allDay
     */
    public boolean isAllDay()
    {
        return allDay;
    }

    /**
     * @param allDay the allDay to set
     */
    public void setAllDay(boolean allDay)
    {
        this.allDay = allDay;
    }
}
