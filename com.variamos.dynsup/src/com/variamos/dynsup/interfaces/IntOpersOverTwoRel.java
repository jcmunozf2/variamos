package com.variamos.dynsup.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.variamos.dynsup.instance.InstElement;
import com.variamos.dynsup.model.ElemAttribute;

/**
 * An interface for SemanticGroupDependency class, required to avoid cyclic
 * references in projects. Part of PhD work at University of Paris 1
 * 
 * @author Juan C. Mu�oz Fern�ndez <jcmunoz@gmail.com>
 * @version 1.1
 * @since 2014-11-24
 * @see com.variamos.semantic.semanticsupport.SemanticGroupDependency
 */
public interface IntOpersOverTwoRel extends Serializable {
	public Set<String> getAllSemanticAttributesNames(List<InstElement> opersParents);

	public List<String> getPropVisibleAttributes(List<InstElement> opersParents);

	public List<String> getPropEditableAttributes(List<InstElement> opersParents);

	public List<String> getPanelSpacersAttributes(List<InstElement> opersParents);

	public List<String> getPanelVisibleAttributes(List<InstElement> opersParents);

	public ElemAttribute getSemanticAttribute(String name,
			List<InstElement> opersParents);

	public String getIdentifier();

	public List<IntOpersRelType> getSemanticRelationTypes();
}