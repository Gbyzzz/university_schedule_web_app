package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception;

public class UniversityServiceException extends Exception {

    public UniversityServiceException(){
        super();
    }
    public UniversityServiceException(String message){
        super(message);
    }
    public UniversityServiceException(Exception e){
        super(e);
    }
    public UniversityServiceException(String message, Exception e){
        super(message, e);
    }
}
