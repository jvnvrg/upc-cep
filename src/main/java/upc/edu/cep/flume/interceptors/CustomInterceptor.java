package upc.edu.cep.flume.interceptors;

import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

public class I1 implements Interceptor {

    @Override
    public void close()
    {

    }

    @Override
    public void initialize()
    {

    }

    @Override
    public Event intercept(Event event)
    {
//        byte[] eventBody = event.getBody();
//
//
//        byte[] modifiedEvent = "world".getBytes();
//
//        event.setBody(modifiedEvent);


        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events)
    {
        for (Event event : events){

            intercept(event);
        }

        return events;
    }

    public static class Builder implements Interceptor.Builder
    {
        @Override
        public void configure(Context context) {
            // TODO Auto-generated method stub
            System.out.println(context.getString("aaa"));
        }

        @Override
        public Interceptor build() {
            return new I1();
        }
    }
}