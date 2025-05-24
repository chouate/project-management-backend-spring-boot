package com.hps.clientservice.patchers;

import com.hps.clientservice.dtos.ClientDTO;
import com.hps.clientservice.entities.Client;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClientPatcher {

    private static final Set<String> IGNORED_FIELDS = Set.of("id","createdAt","updatedAt");
    public static Client clientPatcher(Client existingClient, ClientDTO incompleteClient) throws IllegalAccessException {
        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> clientDtoClass = ClientDTO.class;

        Field[] dtoListFields = clientDtoClass.getDeclaredFields();
        Field[] targetFields = Client.class.getDeclaredFields();

        Map<String, Field> targetFieldMap = Arrays.stream(targetFields)
                        .collect(Collectors.toMap(Field::getName, f -> f));

        System.out.println("Nomber of fields in ClientDTO class : "+dtoListFields.length);
        System.out.println("**************************************************");

        for (Field dtoField : dtoListFields){
            dtoField.setAccessible(true);
            Object value = dtoField.get(incompleteClient);

            if (value != null && targetFieldMap.containsKey(dtoField.getName()) && !IGNORED_FIELDS.contains(dtoField.getName())){
                Field existingField = targetFieldMap.get(dtoField.getName());
                existingField.setAccessible(true);
                existingField.set(existingClient, value);
            }
        }

//        for(Field field:dtoListFields){
//            //IGNORE SOMME FIELDS FOR CHANGE
//            if(IGNORED_FIELDS.contains(field.getName())){
//                continue;
//            }
//            System.out.println(field.getName());
//            //CANT ACCESS IF THE FIELD IS PRIVATE
//            field.setAccessible(true);
//
//            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING CLIENT
//            Object value = field.get(incompleteClient);
//            if(value != null){
//                field.set(existingClient, value);
//            }
//            //MAKE THE FIELD PRIVATE AGAIN
//            field.setAccessible(false);
//        }
        return existingClient;
    }
}
