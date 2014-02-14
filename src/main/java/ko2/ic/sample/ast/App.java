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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

public class App {

    public static void main(String[] args) {
        StringBuilder filePath = new StringBuilder();
        filePath.append("src").append(File.separator);
        filePath.append("main").append(File.separator);
        filePath.append("java").append(File.separator);
        filePath.append("sample").append(File.separator);
        filePath.append("ParserTarget.java");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toString())));) {
            StringBuffer sb = new StringBuffer();
            List<String> list = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                list.add(line + "\n");
                sb.append(line + "\n");
            }

            ASTParser parser = ASTParser.newParser(AST.JLS4);
            parser.setSource(sb.toString().toCharArray());
            CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
            unit.recordModifications();

            Visitor visitor = new Visitor(unit, list.toArray(new String[list.size()]));
            unit.accept(visitor);

            String code = getCode(sb.toString(), unit);

            System.out.println("--------------------");
            System.out.println(code);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static String getCode(String code, CompilationUnit unit) {
        IDocument eDoc = new Document(code);
        TextEdit edit = unit.rewrite(eDoc, null);
        try {
            edit.apply(eDoc);
            return eDoc.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
