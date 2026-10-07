package ressourceRest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("options")
public class RestOption {

    public static OptionBusiness optB = new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String D) {
        List<Option> l = new ArrayList<Option>();

        if (D == null) {
            l = optB.getListeOptions();
        } else {
            l = optB.getOptionsByDomaine(D);
        }

        if (l.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(200).entity(l).build();
    }

    @POST
    public Response addOption(Option op) {
        if (optB.addOption(op)) {
            return Response.status(200).entity(op).build();
        } else {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int id) {
        if (optB.deleteOption(id)) {
            return Response.status(204).build();
        } else {
            return Response.status(404).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("id") int id, Option op) {
        if (optB.updateOption(id, op)) {
            return Response.status(200).build();
        }

        return Response.status(404).build();
    }

    // Question 6 : récupérer une option par son code
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOption(@PathParam("code") int code) {

        Option option = optB.getOptionByCode(code);

        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(option).build();
    }
}