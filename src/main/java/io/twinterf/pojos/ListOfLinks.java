package io.twinterf.pojos;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;

@RegisterForReflection
public class ListOfLinks {

    private ArrayList<String> links;

    public ListOfLinks(ArrayList<String> links) {
        this.links = links;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
}
