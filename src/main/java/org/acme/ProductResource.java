package org.acme;


import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/")
public class ProductResource {



    @POST
    @Path("uploadItem")
    public Response uploadItem(@RestForm("image") FileUpload file) {
        System.out.println(file.contentType());
        System.out.println(file.uploadedFile());
        return Response.ok().build();
    }
}
