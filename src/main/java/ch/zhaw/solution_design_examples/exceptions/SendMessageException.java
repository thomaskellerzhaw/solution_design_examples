/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.exceptions;

/**
 *
 * @author kell
 */
public class SendMessageException extends Exception {
    public SendMessageException() {
        super();
    }
    
    public SendMessageException(String message) {
        super(message);
    }
    
    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SendMessageException(Throwable cause) {
        super(cause);
    }
}
