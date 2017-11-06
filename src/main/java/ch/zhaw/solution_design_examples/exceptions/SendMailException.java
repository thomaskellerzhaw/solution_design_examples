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
public class SendMailException extends Exception {
    public SendMailException() {
        super();
    }
    
    public SendMailException(String message) {
        super(message);
    }
    
    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SendMailException(Throwable cause) {
        super(cause);
    }
}
