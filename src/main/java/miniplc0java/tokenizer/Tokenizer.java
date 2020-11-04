package miniplc0java.tokenizer;

import miniplc0java.error.TokenizeError;
import miniplc0java.error.ErrorCode;
import miniplc0java.util.Pos;

public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     * 
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        if (Character.isDigit(peek)) {
            return lexUInt();
        } else if (Character.isAlphabetic(peek)) {
            return lexIdentOrKeyword();
        } else {
            return lexOperatorOrUnknown();
        }
    }

    private Token lexUInt() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字为止:
        // -- 前进一个字符，并存储这个字符
        //
        String temps = new String();
        Pos startpos = it.currentPos();
        while(Character.isDigit(it.peekChar())){
            temps+=it.nextChar();
        }
        Pos endpos = it.currentPos();
        // 解析存储的字符串为无符号整数
        // 解析成功则返回无符号整数类型的token，否则返回编译错误
        //
        int tempi = Integer.parseInt(temps);
        return new Token(TokenType.Uint,tempi,startpos, endpos);
        // Token 的 Value 应填写数字的值
        //throw new Error("Not implemented");
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        //
        // 尝试将存储的字符串解释为关键字
        // -- 如果是关键字，则返回关键字类型的 token
        // -- 否则，返回标识符
        //
        String temps = new String();
        Pos startpos = it.currentPos();
        while(Character.isLetterOrDigit(it.peekChar())){
            temps+=it.nextChar();
        }
        Pos endpos = it.currentPos();
        if(temps.equals("BEGIN")){
            return new Token(TokenType.Begin,temps,startpos,endpos);
        }
        else if(temps.equals("END")){
            return new Token(TokenType.End,temps,startpos,endpos);
        }
        else if(temps.equals("VAR")){
            return new Token(TokenType.Var,temps,startpos,endpos);
        }
        else if(temps.equals("CONST")){
            return new Token(TokenType.Const,temps,startpos,endpos);
        }
        else if(temps.equals("PRINT")){
            return new Token(TokenType.Print,temps,startpos,endpos);
        }
        else
        return new Token(TokenType.Ident,temps,startpos, endpos);
        // Token 的 Value 应填写标识符或关键字的字符串
        //throw new Error("Not implemented");
    }

    private Token lexOperatorOrUnknown() throws TokenizeError {
        switch (it.nextChar()) {
            case '+':
                return new Token(TokenType.Plus, '+', it.previousPos(), it.currentPos());

            case '-':
                // 填入返回语句
                return new Token(TokenType.Plus, '-', it.previousPos(), it.currentPos());
                //throw new Error("Not implemented");

            case '*':
                // 填入返回语句
                return new Token(TokenType.Plus, '*', it.previousPos(), it.currentPos());
                //throw new Error("Not implemented");

            case '/':
                // 填入返回语句
                return new Token(TokenType.Plus, '/', it.previousPos(), it.currentPos());
                //throw new Error("Not implemented");

            // 填入更多状态和返回语句
            case '=':
                // 填入返回语句
                return new Token(TokenType.Plus, '=', it.previousPos(), it.currentPos());
            //throw new Error("Not implemented");
            case ';':
                // 填入返回语句
                return new Token(TokenType.Plus, ';', it.previousPos(), it.currentPos());
            //throw new Error("Not implemented");
            case '(':
                // 填入返回语句
                return new Token(TokenType.Plus, '(', it.previousPos(), it.currentPos());
            //throw new Error("Not implemented");
            case ')':
                // 填入返回语句
                return new Token(TokenType.Plus, ')', it.previousPos(), it.currentPos());
            //throw new Error("Not implemented");

            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }
}
