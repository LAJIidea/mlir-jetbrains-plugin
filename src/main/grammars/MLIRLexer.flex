package com.kingkiller.mlir.lexer;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.kingkiller.mlir.psi.MLIRTypes.*;

%%

%{
  public _MLIRLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _MLIRLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

LINE_BREAK=[\r\n]
