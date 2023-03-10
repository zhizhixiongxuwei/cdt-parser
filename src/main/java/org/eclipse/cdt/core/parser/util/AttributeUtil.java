/*******************************************************************************
 * Copyright (c) 2012 Google, Inc and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	   Sergey Prigogin (Google) - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.core.parser.util;

import org.eclipse.cdt.core.dom.ast.IASTAttribute;
import org.eclipse.cdt.core.dom.ast.IASTAttributeOwner;
import org.eclipse.cdt.core.dom.ast.IASTToken;
import org.eclipse.cdt.core.parser.StandardAttributes;

/**
 * Collection of static methods for dealing with attributes.
 * @see IASTAttribute
 * @see IASTAttributeOwner
 * @since 5.4
 */
public class AttributeUtil {
	private static final String[] ATTRIBUTE_NORETURN = new String[] { "__noreturn__", StandardAttributes.NORETURN }; //$NON-NLS-1$
	private static final String[] ATTRIBUTE_NODISCARD = new String[] { "warn_unused_result", //$NON-NLS-1$
			StandardAttributes.NODISCARD };

	// Not instantiatable.
	private AttributeUtil() {
	}

	/**
	 * Returns {@code true} if a declarator has an attribute with one of the given names.
	 * The {@code names} array is assumed to be small.
	 */
	public static boolean hasAttribute(IASTAttributeOwner node, String[] names) {
		IASTAttribute[] attributes = node.getAttributes();
		for (IASTAttribute attribute : attributes) {
			char[] name = attribute.getName();
			for (int i = 0; i < names.length; i++) {
				if (CharArrayUtils.equals(name, names[i]))
					return true;
			}
		}
		return false;
	}

	/**
	 * Returns {@code true} if the node has a "noreturn" or "__noreturn__" attribute.
	 */
	public static boolean hasNoreturnAttribute(IASTAttributeOwner node) {
		return hasAttribute(node, ATTRIBUTE_NORETURN);
	}

	/**
	 * Returns {@code true} if the node has a "nodiscard" attribute.
	 * @since 7.0
	 */
	public static boolean hasNodiscardAttribute(IASTAttributeOwner node) {
		return hasAttribute(node, ATTRIBUTE_NODISCARD);
	}

	/**
	 * Returns character representation of the attribute argument, or {@code null} if the attribute
	 * has zero or more than one argument.
	 */
	public static char[] getSimpleArgument(IASTAttribute attribute) {
		IASTToken argumentClause = attribute.getArgumentClause();
		return argumentClause == null ? null : argumentClause.getTokenCharImage();
	}
}
