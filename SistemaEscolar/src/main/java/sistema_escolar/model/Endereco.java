/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable
{
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "endereco_ID")
    private Long id;
    
    @NotNull(message = "Preencha o campo RUA")
    @Size(min = 1, max = 150, message = "A rua deve ter entre 1 e 150 "
            + "caracteres!")
    @Column(name = "endereco_RUA", length = 200)
    private String rua;
    
    @NotNull(message = "Preencha o campo NUMERO")
    @Pattern(regexp = "^[0-9]{1,5}$", message = "O número de rua deve ter entre 1 e 5 "
            + "digítos!")
    @Column(name = "endereco_NUMERO")
    private String numero;
    
    
    @NotNull(message = "Preencha o campo BAIRRO")
    @Size(min = 1, max = 50, message = "O bairro deve ter entre 1 e 50 "
            + "caracteres!")
    @Column(name = "endereco_BAIRRO", length = 75)
    private String bairro;
    
    @NotNull(message = "Preencha o campo CIDADE")
    @Size(min = 1, max = 100, message = "A cidade deve ter entre 1 e 100 "
            + "caracteres!")
    @Column(name = "endereco_CIDADE", length = 120)
    private String cidade;
    
    @NotNull(message = "Peencha o campo CEP")
    @Column(name = "endereco_CEP")
    private String cep;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.rua);
        hash = 29 * hash + Objects.hashCode(this.numero);
        hash = 29 * hash + Objects.hashCode(this.bairro);
        hash = 29 * hash + Objects.hashCode(this.cidade);
        hash = 29 * hash + Objects.hashCode(this.cep);
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
        final Endereco other = (Endereco) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.rua, other.rua))
        {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero))
        {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro))
        {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade))
        {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep))
        {
            return false;
        }
        return true;
    }
    
     /**
     * @return the serialVersionUID
     */
    public static Long getSerialVersionUID()
    {
        return serialVersionUID;
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
     * @return the rua
     */
    public String getRua()
    {
        return rua;
    }

    /**
     * @param rua the rua to set
     */
    public void setRua(String rua)
    {
        this.rua = rua;
    }

    /**
     * @return the numero
     */
    public String getNumero()
    {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    /**
     * @return the bairro
     */
    public String getBairro()
    {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro)
    {
        this.bairro = bairro;
    }

    /**
     * @return the cidade
     */
    public String getCidade()
    {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    /**
     * @return the cep
     */
    public String getCep()
    {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep)
    {
        this.cep = cep;
    }
    
    
}
