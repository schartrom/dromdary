/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.generator.model2model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.openarchitectureware.workflow.WorkflowContext;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.lib.SimpleJavaModificationComponent;
import org.openarchitectureware.workflow.monitor.ProgressMonitor;

public class ModelTransformerBva extends SimpleJavaModificationComponent {

	@Override
	protected void doModification(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues, Object model) {
		ModelImpl dm = (ModelImpl) model;
		EList<NamedElement> elms = dm.getMembers();
		for (NamedElement namedElement : elms) {
			// System.out.println(namedElement);
			EList<Element> elements = namedElement.allOwnedElements();
			for (Element element : elements) {
				if (element instanceof ClassImpl) {
					ClassImpl impl = (ClassImpl) element;

					removePrefix(impl.getAppliedStereotypes());
				
					EList<Operation> operations = impl.getAllOperations();
					for (Operation operation : operations) {
						removePrefix(operation.getAppliedStereotypes());
					}
					
					EList<Property> attr = impl.getAllAttributes();
					for (Property property : attr) {
						removePrefix(property.getAppliedStereotypes());
					}
				}
			}
		}
	}
	
	/*
	 * Removes the prefix "BVA_" from stereotypes
	 */
	private void removePrefix( EList<Stereotype> stereotypes ) {
		for (Stereotype stereotype : stereotypes) {
			String nameTemp = stereotype.getName();
			
			if (nameTemp.contains("BVA_")) {
				stereotype.setName(nameTemp.substring(4,nameTemp.length()));
			}
		}
	}
}