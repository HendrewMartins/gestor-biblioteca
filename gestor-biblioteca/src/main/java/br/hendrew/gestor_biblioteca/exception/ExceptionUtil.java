package br.hendrew.gestor_biblioteca.exception;


public class ExceptionUtil {

    public static String getMensagem(Exception e) {
        if (e instanceof RestWarnException){
            return e.getMessage();
        }
        if (e instanceof RestErrorException){
            return e.getMessage();
        }
        String mensagem = "classe: " + e.getClass().toString();
        if (e.getCause() != null){
            if (e.getCause().getCause() != null){
                mensagem += "\ncausa: " + e.getCause().getCause().toString();
            }else{
                mensagem += "\ncausa: " + e.getCause().toString();
            }
        }
        if (e.getStackTrace() != null){
            int linha = 0;
            for (StackTraceElement traceElement : e.getStackTrace()) {
                if (traceElement != null && (linha > 0 || traceElement.toString().startsWith("br.inf.ids"))){
                    if (linha == 0){
                        mensagem += "\nLinha: ";
                    }
                    mensagem += "\n*" + traceElement.toString();
                    linha++;
                    if (linha >= 5){
                        break;
                    }
                }
            }
        }
        if (e.getMessage() != null){
            mensagem += "\nmensagem: " + e.getMessage();
        }
        return mensagem;
    }

}

