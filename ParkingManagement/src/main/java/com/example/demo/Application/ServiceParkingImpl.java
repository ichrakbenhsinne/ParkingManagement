package com.example.demo.Application;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.API.DTOs.AddParkingDTO;
import com.example.demo.API.DTOs.GetBlockDTO;
import com.example.demo.API.DTOs.GetParkingDTO;
import com.example.demo.API.DTOs.GetPlaceDTO;
import com.example.demo.Domain.Address;
import com.example.demo.Domain.AddressRepository;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.BlockRepository;
import com.example.demo.Domain.BlockType;
import com.example.demo.Domain.GeoPoint;
import com.example.demo.Domain.GeopointRepository;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.ParkingRepository;
import com.example.demo.Domain.ParkingType;
import com.example.demo.Domain.Places;
import com.example.demo.Domain.Status;
import com.example.demo.Domain.placesRepository;
import com.example.demo.Exceptions.DeleteImpossible;
import com.example.demo.Exceptions.ObjectNotFoundException;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServiceParkingImpl implements ServiceParking {
    
    @Autowired 
    ParkingRepository parkingrepository;
    @Autowired  
    BlockRepository blockrepository;
    @Autowired 
    placesRepository placerepository;
    @Autowired 
    GeopointRepository geopointrepository;
    @Autowired
    AddressRepository addressrepository;
 
    @Override
public Parking addParking(String name, String description, ParkingType parkingType,
                          double langitudeGeoPoint, double latitudeGeoPoint,
                          String cityAddress, Integer codeZoneAddress, String streetAddress, String locationAddress, Status state) {

    // Create a new Parking instance with the provided parameters
    Parking parking = new Parking();
    parking.setName(name);
    parking.setDescription(description);
    parking.setCapacity(0);
    parking.setParkingType(parkingType);
    parking.setState(state);
    // Save Parking entity
    parking = parkingrepository.save(parking);

    // Set GeoPoint
    GeoPoint geoPoint = new GeoPoint();
    geoPoint.setLongitude(langitudeGeoPoint);
    geoPoint.setLatitude(latitudeGeoPoint);
    geoPoint.setParking(parking); // Set the association to Parking
    geopointrepository.save(geoPoint); // Save GeoPoint

    // Set Address
    Address address = new Address();
    address.setCity(cityAddress);
    address.setCodeZone(codeZoneAddress);
    address.setStreet(streetAddress);
    address.setLocation(locationAddress);
    address.setParking(parking); // Set the association to Parking
    addressrepository.save(address); // Save Address

    // Set the associations in Parking
    parking.setCoordinate(geoPoint);
    parking.setParkingAddress(address);

    // Save the Parking instance again to update associations
    parking = parkingrepository.save(parking);

    return parking;


}

@Override
public Parking updateParking(Parking newParking, Long id) {
    Parking parkingToUpdate = parkingrepository.findparkingById(id);
    System.out.println(newParking.getState());
    
    if (parkingToUpdate != null) {
        parkingToUpdate.setName(newParking.getName());
        parkingToUpdate.setDescription(newParking.getDescription());
        
        // Vérifier si l'objet GeoPoint existant dans le parking est null
        if (parkingToUpdate.getCoordinate() == null) {
            parkingToUpdate.setCoordinate(new GeoPoint()); // Créer un nouvel objet GeoPoint si null
        }
        
        // Vérifier si la latitude et la longitude de la nouvelle position sont null
        if (newParking.getCoordinate() != null) {
            // Assurez-vous que les coordonnées de la nouvelle position sont correctement définies dans l'objet GeoPoint
            parkingToUpdate.getCoordinate().setLatitude(newParking.getCoordinate().getLatitude());
            parkingToUpdate.getCoordinate().setLongitude(newParking.getCoordinate().getLongitude());
        }

        parkingToUpdate.setParkingAddress(newParking.getParkingAddress());
        parkingToUpdate.setParkingType(newParking.getParkingType());
        parkingToUpdate.setState(newParking.getState());
       
        return parkingrepository.save(parkingToUpdate);
    } else {
        return null; // Parking non trouvé, renvoie null
    }
}

    @Override
public void deleteParking(String parkingName) {
    // Recherche du parking à supprimer par son nom
    Parking parkingToDelete = parkingrepository.findParkingbyidentf(parkingName);

    // Vérification si le parking existe
    if (parkingToDelete != null) {
        // Vérifie si la liste des blocs est nulle ou vide
        if (parkingToDelete.getBlocks() == null || parkingToDelete.getBlocks().isEmpty()) {
            // Si oui, supprimez le parking
            addressrepository.delete(parkingToDelete.getParkingAddress());
            geopointrepository.delete(parkingToDelete.getCoordinate());
            parkingrepository.delete(parkingToDelete);
        } else {
            // Sinon, supprimez chaque bloc associé au parking
            for (Block block : parkingToDelete.getBlocks()) {
                deleteBlock(block.getBlockName());
            }

            // Après avoir supprimé tous les blocs, supprimez les autres entités liées au parking
             addressrepository.delete(parkingToDelete.getParkingAddress());
            geopointrepository.delete(parkingToDelete.getCoordinate());
            parkingrepository.delete(parkingToDelete);
        }
    } else {
        // Gérer le cas où le parking n'existe pas
        // Vous pouvez lever une exception, retourner un code d'erreur, etc.
        // Par exemple, vous pouvez lever une exception NotFoundException
    }
}


    


    @Override
public Parking getParkingByName(String parkingName) {
    return parkingrepository.findParkingbyidentf(parkingName);

}


@Override
public List<GetParkingDTO> getAllParkings() {
    List<Parking> allParkings = parkingrepository.findAll();
    List<GetParkingDTO> parkingDTOs = new ArrayList<>();

    for (Parking parking : allParkings) {
        // Vérification de nullité de l'adresse
        if (parking.getParkingAddress() != null) {
            parkingDTOs.add(GetParkingDTO.mapFromParking(parking));
        }
    }

    return parkingDTOs;
}


// 
@Override
public void addBlockToParking(String parkingName, Block block) {
    try {
        if (StringUtils.isBlank(parkingName) || block == null) {
            throw new IllegalArgumentException("ParkingName or block cannot be null or empty.");
        }

        Parking parking = parkingrepository.findParkingbyidentf(parkingName);
        if (parking == null) {
            throw new IllegalArgumentException("Parking not found with name: " + parkingName);
        }

        // Validate block properties
        if (StringUtils.isBlank(block.getBlockName()) || block.getCapacity() == null || block.getCapacity() <= 0) {
            throw new IllegalArgumentException("Block name or capacity is invalid.");
        }

      block.setParking(parking);
        // Save the block to ensure it has an ID
        Block savedBlock = blockrepository.save(block);
        
        // Generate places for the block
        List<Places> places = generatePlaces(savedBlock.getCapacity(), savedBlock.getBlockName(), savedBlock);

        // Add block to parking and save both
        parking.getBlocks().add(savedBlock);
        parkingrepository.save(parking);

        // Save places
        placerepository.saveAll(places);

    } catch (IllegalArgumentException e) {
        throw e; // Re-throw IllegalArgumentException with the original message
    } catch (Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        System.out.println(stackTrace);
        throw new RuntimeException("Error adding block to parking: " + e.getMessage(), e);
    }
}

private List<Places> generatePlaces(Integer capacity, String blockName, Block block) {
    List<Places> places = new ArrayList<>();

    for (int i = 1; i <= capacity; i++) {
        Places place = new Places();
        place.setname(blockName + i);
        place.setState(true);
        place.setBlock(block);
        places.add(place);
    }
    return places;
}


////////////////////////////////////////////
private List<String> generatePlaceNames(int capacity, String BlockName) {
    List<String> places = new ArrayList<>();
    for (int i = 1; i <= capacity; i++) {
        places.add(BlockName + i);
       Places place = new Places();
       place.setname(BlockName + i);
       place.setState(false);
      // place.setBlockname(BlockName);
       placerepository.save(place);

    }
    return places;
}
////////////////////////////////////////////////



 // update selon des cond

 @Override
 public GetBlockDTO updateBlock(Long id, Block newBlock) {
     Block blockToUpdate = blockrepository.findBlockByid(id);
     if (blockToUpdate != null) {
         blockToUpdate.setBlockName(newBlock.getBlockName());
         blockToUpdate.setBlockType(newBlock.getBlockType());
         blockToUpdate.setCapacity(newBlock.getCapacity());
         blockToUpdate.setIsOppen(newBlock.getIsOppen());
 
         // Assurez-vous de sauvegarder la mise à jour du bloc
         Block updatedBlock = blockrepository.save(blockToUpdate);
         
         // Assurez-vous de retourner le DTO mis à jour à partir du bloc mis à jour
         return GetBlockDTO.mapFromBlock(updatedBlock);
     } else {
         return null;
     }
 }
 


    @Override
public void fermerBlock(String BlockName, String ParkingName) {
    Parking parking = parkingrepository.findParkingbyidentf(ParkingName);
    if (parking != null) {
        Block block = blockrepository.findBlockByBlockName(BlockName);
        if (block != null) {
            block.setIsOppen(false); // Correction de la faute de frappe
            // Mise à jour dans la base de données
            blockrepository.save(block);
        } else {
            throw new ObjectNotFoundException("Block not found");
        }
    } else {
        throw new ObjectNotFoundException("Parking not found");
    }
}


    @Override
    public void ouvrirBlock(String BlockName, String ParkingName) {
        Parking parking = parkingrepository.findParkingbyidentf(ParkingName);
        if (parking != null) {
            Block block = blockrepository.findBlockByBlockName(BlockName);
            if (block != null) {
                block.setIsOppen(true); // Correction de la faute de frappe
                // Mise à jour dans la base de données
                blockrepository.save(block);
            } else {
                throw new ObjectNotFoundException("Block not found");
            }
        } else {
            throw new ObjectNotFoundException("Parking not found");
        } 
    }


    @Override
    public void addPlaceToBlock(String blockName, Places place) {
        try {
            // Vérifier si le bloc existe déjà dans la base de données
            Block existingBlock = blockrepository.findBlockByBlockName(blockName);
    
            if (existingBlock != null) {
                // Créer une nouvelle place à partir des données fournies
                Places newPlace = new Places();
                newPlace.setname(place.getname());
                newPlace.setState(place.isState());
                newPlace.setBlock(existingBlock); // Définir le bloc pour la nouvelle place
    
                // Ajouter la nouvelle place au bloc existant
                existingBlock.getPlaces().add(newPlace);
    
                // Sauvegarder le bloc mis à jour dans la base de données, ce qui enregistre également la nouvelle place
                blockrepository.save(existingBlock);
            } else {
                System.out.println("Block doesn't exist");
            }
        } catch (Exception e) {
            System.out.println("Error adding place to block: " + e.getMessage());
        }
    }
    


////////////////////////////////////////////////
    @Override
    public void fermerParking(String ParkingName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FermerParking'");
    }

    @Override
    public void ouvrirParking(String ParkingName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'OuvrirParking'");
    }
//////////////////////////////////////////////////


/// publier le parking dans le map 
    @Override
    public Void publierParking(Parking parking) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'PublierParking'");
    }

    @Override
    public Block addBlock( Integer capacity, String blockName, BlockType blockType
            ) {
    
        // Créer un nouveau bloc
        Block block = new Block();
    
        // Générer un nouvel ID pour le bloc
       // String newId = UUID.randomUUID().toString();
      //  block.setBlockId(newId);
    
        // Définir les propriétés du bloc
        block.setCapacity(capacity);
        block.setBlockName(blockName);
        block.setBlockType(blockType);
       
    
        // Ajouter des places au bloc
        List<Places> places = new ArrayList<>();
       
        block.setPlaces(places);
    
        // Enregistrer le bloc dans le référentiel
        return blockrepository.save(block);
    }

    @Override
    public Block getBlockByName(String BlockName) {
        // TODO Auto-generated method stub

      return blockrepository.findBlockByBlockName(BlockName);

        }

    @Override
    public Places getPlaceByName(String PlaceName) {
        // TODO Auto-generated method stub
       
        return placerepository.findPlaceByName(PlaceName);
    
    }

    @Override
public List<Places> getPlacesByBlock(String BlockName) {
    // Récupérer le bloc existant par son nom
    Block existingBlock = blockrepository.findBlockByBlockName(BlockName);

    // Vérifier si le bloc existe
    if (existingBlock == null) {
        throw new IllegalArgumentException("Block not found with name: " + BlockName);
    }

    // Récupérer les places associées à ce bloc
    List<Places> places = existingBlock.getPlaces();

    // Retourner la liste des places
    return places;
}

@Override
public List<Block> getBlocksByParking(String ParkingName) {
    // Récupérer le parking existant par son nom
    Parking existingParking = parkingrepository.findParkingbyidentf(ParkingName);

    // Vérifier si le parking existe
    if (existingParking == null) {
        throw new IllegalArgumentException("Parking not found with name: " + ParkingName);
    }

    // Récupérer les blocs associés au parking
    List<Block> blocks = existingParking.getBlocks();

    // Retourner la liste des blocs
    return blocks;
}

    @Override
    public void deletePlaceByName(String placeName, String blockName) {
        // Trouver la place à supprimer
        Places place = placerepository.findPlaceByName(placeName);
        if (place == null) {
            throw new ObjectNotFoundException("La place avec le nom '" + placeName + "' n'a pas été trouvée.");
        }
        
        // Trouver l'objet Block correspondant
        Block block = blockrepository.findBlockByBlockName(blockName);
        if (block == null) {
            throw new ObjectNotFoundException("Le bloc avec le nom '" + blockName + "' n'a pas été trouvé.");
        }
        
        // Supprimer la place de la liste des places de l'objet Block
        if (block.getPlaces().contains(place)) {
            block.getPlaces().remove(place);
            blockrepository.save(block);
        } else {
            throw new ObjectNotFoundException("La place avec le nom '" + placeName + "' n'est pas présente dans le bloc '" + blockName + "'.");
        }
        // Supprimer la place de la base de données
        placerepository.deleteById(place.getId());
    }

    @Override
    public Boolean updatePlaceStateToAvailable(String ParkingName, String Blockname, String PlaceName) {
        // TODO Auto-generated method stub
       
        Parking parking = parkingrepository.findParkingbyidentf(ParkingName);
        if (parking != null) {
            // Recherche du bloc
            Block block = blockrepository.findBlockByBlockName(Blockname);
            if (block != null) {
                // Recherche de la place dans le bloc
                Places place = placerepository.findPlaceByName(PlaceName);
                if (place != null) {
                    // Mise à jour de l'état de la place
                    place.setState(true);
                    placerepository.save(place); // Sauvegarde des modifications
                    return true; // Retourne true si la mise à jour a réussi
                } else {
                    // La place n'a pas été trouvée dans le bloc spécifié
                    throw new ObjectNotFoundException("Place not found in the specified block");
                }
            } else {
                // Le bloc n'a pas été trouvé dans le parking spécifié
                throw new ObjectNotFoundException("Block not found in the specified parking");
            }
        } else {
            // Le parking n'a pas été trouvé
            throw new ObjectNotFoundException("Parking not found");
        }
    }

    @Override
public void deleteBlock(String blockName) {
    // Recherche du bloc à supprimer par son nom
    Block block = blockrepository.findBlockByBlockName(blockName);
    
    // Vérifie si le bloc existe
    if (block != null) {
        // Vérifie si toutes les places sont libres
        boolean allPlacesFree = true;
        for (Places place : block.getPlaces()) {
            if (!place.isState()) {
                allPlacesFree = false;
                break;
            }
        }
        
        if (allPlacesFree) {
            // Supprime toutes les places associées
            for (Places place : block.getPlaces()) {
                placerepository.delete(place);
            }
            
            // Ensuite, supprime le bloc lui-même
            blockrepository.delete(block);
        } else {
            
            throw new DeleteImpossible("impossible de supprimer le block! il ya des places reservee !!");
            // Gérer le cas où des places sont réservées
            // Vous pouvez lever une exception, retourner un code d'erreur, etc.
            // Par exemple, vous pouvez lever une exception BlockNotFreeException
        }
    } else {
        throw new ObjectNotFoundException("block not found");
        // Gérer le cas où le bloc n'existe pas
        // Vous pouvez lever une exception, retourner un code d'erreur, etc.
        // Par exemple, vous pouvez lever une exception NotFoundException
    }
}

@Override
public GetPlaceDTO updatePlace(Long id, Places newPlace) {
    // Récupérer l'emplacement à mettre à jour
    Places placeToUpdate = placerepository.findPlaceByid(id);
    
    if (placeToUpdate != null) {
        // Mettre à jour les propriétés de l'emplacement avec les nouvelles valeurs
        placeToUpdate.setname(newPlace.getname());
        placeToUpdate.setState(newPlace.isState());

        // Assurer la sauvegarde de la mise à jour de l'emplacement
        Places updatedPlace = placerepository.save(placeToUpdate);

        // Retourner le DTO mis à jour à partir de l'emplacement mis à jour
        return GetPlaceDTO.mapFromPlaces(updatedPlace);
    } else {
        return null; // Ou lancez une exception pour indiquer que l'emplacement n'a pas été trouvé
    }
}

    
    
       
}