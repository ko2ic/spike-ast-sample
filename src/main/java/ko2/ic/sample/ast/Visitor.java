/*******************************************************************************
 * Copyright (c) 2014 2014/02/15
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Kouji Ishii - initial implementation
 *******************************************************************************/
package ko2.ic.sample.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.base.Joiner;

public class Visitor extends ASTVisitor {

    private final CompilationUnit compilationUnit;

    private final String[] source;

    public Visitor(CompilationUnit compilationUnit, String[] source) {
        super();
        this.compilationUnit = compilationUnit;
        this.source = source;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean visit(CompilationUnit node) {
        for (Comment comment : (List<Comment>) node.getCommentList()) {
            comment.accept(this);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(LineComment node) {
        int startLineNumber = compilationUnit.getLineNumber(node.getStartPosition()) - 1;
        String lineComment = source[startLineNumber].trim();
        System.out.println(String.format("%d = %s", startLineNumber + 1, lineComment));
        return super.visit(node);
    }

    @Override
    public boolean visit(BlockComment node) {
        int startLineNumber = compilationUnit.getLineNumber(node.getStartPosition()) - 1;
        int endLineNumber = compilationUnit.getLineNumber(node.getStartPosition() + node.getLength()) - 1;
        StringBuffer blockComment = new StringBuffer();
        for (int lineCount = startLineNumber; lineCount <= endLineNumber; lineCount++) {
            String blockCommentLine = source[lineCount].trim();
            blockComment.append(blockCommentLine);
            if (lineCount != endLineNumber) {
                blockComment.append("\n");
            }
        }
        System.out.println(String.format("%d = %s", startLineNumber + 1, blockComment.toString()));
        return super.visit(node);
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        StringBuffer sb = new StringBuffer();

        sb.append(Joiner.on(' ').join(node.modifiers()));
        sb.append(" ");

        if (!node.isConstructor()) {
            sb.append(node.getReturnType2().toString());
            sb.append(" ");
        }
        sb.append(String.format("%s(%s)", node.getName().toString(), Joiner.on(',').join(node.parameters())));
        System.out.println(sb);
        return super.visit(node);
    }

    @Override
    public boolean visit(Javadoc node) {
        node.delete();
        return super.visit(node);
    }
}
