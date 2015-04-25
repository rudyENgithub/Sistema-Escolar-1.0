/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import org.primefaces.model.LazyDataModel;
import sistema_escolar.lazyloadmodel.AlunoLazyModel;
import sistema_escolar.lazyloadmodel.MateriaLazyModel;
import sistema_escolar.lazyloadmodel.ProfessorLazyModel;
import sistema_escolar.lazyloadmodel.SerieLazyModel;
import sistema_escolar.lazyloadmodel.TurmaLazyModel;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Materia;
import sistema_escolar.model.Professor;
import sistema_escolar.model.Serie;
import sistema_escolar.model.Turma;
import sistema_escolar.qualifier.LazyModel;

/**
 *
 * @author Vitor Freitas
 */
public class LazyModelProducer
{
    @Produces 
    @RequestScoped 
    @LazyModel(LazyModel.Type.PROFESSOR)
    public LazyDataModel<Professor> createProfessorLazyModel(@New ProfessorLazyModel model)
    {
        return model;
    }
    
    @Produces 
    @RequestScoped 
    @LazyModel(LazyModel.Type.ALUNO)
    public LazyDataModel<Aluno> createProfessorLazyModel(@New AlunoLazyModel model)
    {
        return model;
    }
    
    @Produces 
    @RequestScoped 
    @LazyModel(LazyModel.Type.SERIE)
    public LazyDataModel<Serie> createProfessorLazyModel(@New SerieLazyModel model)
    {
        return model;
    }
    
    @Produces 
    @RequestScoped 
    @LazyModel(LazyModel.Type.TURMA)
    public LazyDataModel<Turma> createProfessorLazyModel(@New TurmaLazyModel model)
    {
        return model;
    }
    
    @Produces 
    @RequestScoped 
    @LazyModel(LazyModel.Type.MATERIA)
    public LazyDataModel<Materia> createProfessorLazyModel(@New MateriaLazyModel model)
    {
        return model;
    }
}
