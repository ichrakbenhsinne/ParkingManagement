package com.example.demo.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.API.DTOs.GetParkingDTO;
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
import com.example.demo.Domain.placeRepository;
import com.example.demo.Exceptions.ObjectNotFoundException;

import io.micrometer.common.util.StringUtils;

@Service
public class ServiceParkingImpl implements ServiceParking {
    
    @Autowired 
    ParkingRepository parkingrepository;
    @Autowired  
    BlockRepository blockrepository;
    @Autowired 
    placeRepository placerepository;
    @Autowired 
    GeopointRepository geopointrepository;
    @Autowired
    AddressRepository addressrepository;
 
    @Override
    public Parking addParking(String name, String description, ParkingType parkingType,
                               double langitudeGeoPoint, double latitudeGeoPoint,
                              String cityAddress, String codeZoneAddress, String streetAddress, String locationAddress) {

        // Create a new Parking instance with the provided parameters
        Parking parking = new Parking();
        String newId = UUID.randomUUID().toString();
        parking.setParkingId(newId);
        parking.setName(name);
        parking.setDescription(description);
        parking.setCapacity(0);
        parking.setParkingType(parkingType);

        // Set other properties for the GeoPoint and Address (assuming you have corresponding setters)
        GeoPoint geoPoint = new GeoPoint();
        geoPoint.setLongitude(langitudeGeoPoint);
        geoPoint.setLatitude(latitudeGeoPoint);
        geopointrepository.save(geoPoint);
        parking.setCoordinate(geoPoint);
        
        Address address = new Address();
        address.setCity(cityAddress);
        address.setCodeZone(Integer.parseInt(codeZoneAddress)); // Assuming codeZone is an Integer
        address.setStreet(streetAddress);
        address.setLocation(locationAddress);
        parking.setParkingAddress(address);
        addressrepository.save(address);
        // Allocate and add blocks
        List<String> blocksnames = new ArrayList<>();
       
        parking.setBlocksNames(blocksnames);

        // Save the Parking instance to the repository
        return parkingrepository.save(parking);
    }

    @Override
    public Parking UpdateParking(Parking NewParking, String ParkingName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateParking'");
    }

    @Override
    public void DeleteParking(String parkingName) {
        // Recherche du parking à supprimer par son nom
        Parking parkingToDelete = parkingrepository.findParkingbyidentf(parkingName);

        // Vérification si le parking existe
        if (parkingToDelete != null) {
            // Supprimez le parking du référentiel
            parkingrepository.delete(parkingToDelete);
        } else {
            // Gérer le cas où le parking n'existe pas
            // Vous pouvez lever une exception, retourner un code d'erreur, etc.
            // Par exemple, vous pouvez lever une exception NotFoundException
             }
    }




    @Override
public Parking GetParkingByName(String parkingName) {
    return parkingrepository.findParkingbyidentf(parkingName);
}


@Override
public List<GetParkingDTO> GetAllParkings() {
    List<Parking> allParkings = parkingrepository.findAll();
    List<GetParkingDTO> parkingDTOs = new ArrayList<>();

    for (Parking parking : allParkings) {
        parkingDTOs.add(GetParkingDTO.mapFromParking(parking));
    }

    return parkingDTOs;
}

// 
@Override
public void AddBlockToParking(String ParkingName, Block block) {
    try {
        if (StringUtils.isBlank(ParkingName) || block == null) {
            throw new IllegalArgumentException("ParkingName or block cannot be null or empty.");
        }

        Parking parking = parkingrepository.findParkingbyidentf(ParkingName);

        if (parking == null) {
            throw new IllegalArgumentException("Parking not found with name: " + ParkingName);
        }

      //  Block existingBlock = blockrepository.findBlockByBlockName(block.getBlockName());


        String newBlockId = UUID.randomUUID().toString();
        block.setBlockId(newBlockId);
        block.setParkingname(ParkingName);
        List<String> places = generatePlaceNames(block.getCapacity(), block.getBlockName());
        block.setPlaces(places);
        parking.getBlocksNames().add(block.getBlockName());
        blockrepository.save(block);

        /*if (existingBlock != null) {
            existingBlock.setCapacity(block.getCapacity());
            existingBlock.setBlockType(block.getBlockType());
            existingBlock.setPlaces(block.getPlaces());
            blockrepository.save(existingBlock);
        } else {
            String newBlockId = UUID.randomUUID().toString();
            block.setBlockId(newBlockId);
            block.setParkingname(ParkingName);
            List<String> places = generatePlaceNames(block.getCapacity(), block.getBlockName());
            block.setPlaces(places);
            parking.getBlocksNames().add(block.getBlockName());
            blockrepository.save(block);
        }
*/
        parkingrepository.save(parking);
    } catch (Exception e) {
        throw new RuntimeException("Error adding block to parking: " + e.getMessage(), e);
    }
}


private List<String> generatePlaceNames(int capacity, String BlockName) {
    List<String> places = new ArrayList<>();
    for (int i = 1; i <= capacity; i++) {
        places.add(BlockName + i);
       Places place = new Places();
       place.setName(BlockName + i);
       place.setState(false);
       place.setBlockname(BlockName);
       placerepository.save(place);

    }
    return places;
}
    
 // update selon des cond

    @Override
    public Void UpdateBlock(String BlockName, String ParkingName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateBlock'");
    }



    @Override
public void FermerBlock(String BlockName, String ParkingName) {
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
    public void OuvrirBlock(String BlockName, String ParkingName) {
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
public void AddPlaceToBlock(String BlockName, String ParkingName, Places place) {
    try {
        // Récupérer le parking par son nom
        Parking parking = parkingrepository.findParkingbyidentf(ParkingName);
       
        if (parking != null) {
            // Vérifier si le bloc existe déjà dans la base de données
            Block existingBlock = blockrepository.findBlockByBlockName(BlockName);

            if (existingBlock != null) {
                // Créer une nouvelle place à partir des données fournies
                Places newPlace = new Places();
                newPlace.setName(place.getName());
                newPlace.setState(place.isState());
                newPlace.setBlockname(BlockName);

                // Ajouter la nouvelle place au bloc existant
                existingBlock.getPlaces().add(newPlace.getName());

                // Sauvegarder le bloc mis à jour dans la base de données
                blockrepository.save(existingBlock);

                // Sauvegarder la nouvelle place dans la base de données
                placerepository.save(newPlace);
            } else {
                System.out.println("Block doesn't exist");
            }
            // Sauvegarder le parking mis à jour
            parkingrepository.save(parking);
        } else {
            System.out.println("Parking not found");
        }
    } catch (Exception e) {
        System.out.println("Error adding place to block: " + e.getMessage());
    }
}


////////////////////////////////////////////////
    @Override
    public void FermerParking(String ParkingName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FermerParking'");
    }

    @Override
    public void OuvrirParking(String ParkingName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'OuvrirParking'");
    }
//////////////////////////////////////////////////


/// publier le parking dans le map 
    @Override
    public Void PublierParking(Parking parking) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'PublierParking'");
    }

    @Override
    public Block addBlock( Integer capacity, String blockName, BlockType blockType
            ) {
    
        // Créer un nouveau bloc
        Block block = new Block();
    
        // Générer un nouvel ID pour le bloc
        String newId = UUID.randomUUID().toString();
        block.setBlockId(newId);
    
        // Définir les propriétés du bloc
        block.setCapacity(capacity);
        block.setBlockName(blockName);
        block.setBlockType(blockType);
       
    
        // Ajouter des places au bloc
        List<String> places = new ArrayList<>();
       
        block.setPlaces(places);
    
        // Enregistrer le bloc dans le référentiel
        return blockrepository.save(block);
    }

    @Override
    public Block GetBlockByName(String BlockName) {
        // TODO Auto-generated method stub

      return blockrepository.findBlockByBlockName(BlockName);

        }

    @Override
    public Places GetPlaceByName(String PlaceName) {
        // TODO Auto-generated method stub
       
        return placerepository.findPlacebyidentf(PlaceName);
        
    }

    @Override
    public List<Places> GetplacesByBlock(String BlockName) {
        // Récupérer le bloc existant par son nom
        Block existingBlock = blockrepository.findBlockByBlockName(BlockName);
    
        // Initialiser une liste pour stocker les noms des places
        List<String> placesNames = existingBlock.getPlaces();
        
        // Initialiser une liste pour stocker les objets Places
        List<Places> places = new ArrayList<>();
    
        // Parcourir la liste des noms de places
        for (String placeName : placesNames) {
            // Récupérer l'objet Place correspondant au nom de place
            Places place = placerepository.findPlacebyidentf(placeName);
            
            // Vérifier si l'objet Place existe
            if (place != null) {
                // Ajouter l'objet Place à la liste des places
                places.add(place);
            }
        }
        // Retourner la liste des places
        return places;
    }

    @Override
    public List<Block> GetBlocksByParking(String ParkingName) {
        // TODO Auto-generated method stub
          // Récupérer le bloc existant par son nom
          Parking existingparking = parkingrepository.findParkingbyidentf(ParkingName);
    
          // Initialiser une liste pour stocker les noms des places
          List<String> BlocksNames = existingparking.getBlocksNames();
          
          // Initialiser une liste pour stocker les objets Places
          List<Block> Blocks = new ArrayList<>();
      
          // Parcourir la liste des noms de places
          for (String BlockName : BlocksNames) {
              // Récupérer l'objet Place correspondant au nom de place
              Block block = blockrepository.findBlockByBlockName(BlockName);
              
              // Vérifier si l'objet Place existe
              if (block != null) {
                  // Ajouter l'objet Place à la liste des places
                  Blocks.add(block);
              }
          }
          // Retourner la liste des places
          return Blocks;
    
    }

    @Override
    public void deletePlaceByName(String placeName, String blockName) {
        // Trouver la place à supprimer
        Places place = placerepository.findPlacebyidentf(placeName);
        if (place == null) {
            throw new ObjectNotFoundException("La place avec le nom '" + placeName + "' n'a pas été trouvée.");
        }
        
        // Trouver l'objet Block correspondant
        Block block = blockrepository.findBlockByBlockName(blockName);
        if (block == null) {
            throw new ObjectNotFoundException("Le bloc avec le nom '" + blockName + "' n'a pas été trouvé.");
        }
        
        // Supprimer la place de la liste des places de l'objet Block
        if (block.getPlaces().contains(placeName)) {
            block.getPlaces().remove(placeName);
            blockrepository.save(block);
        } else {
            throw new ObjectNotFoundException("La place avec le nom '" + placeName + "' n'est pas présente dans le bloc '" + blockName + "'.");
        }
        
        // Supprimer la place de la base de données
        placerepository.deleteById(place.getId());
    }

    @Override
    public Boolean UpdatePlaceStateToAvailable(String ParkingName, String Blockname, String PlaceName) {
        // TODO Auto-generated method stub
       
        Parking parking = parkingrepository.findParkingbyidentf(ParkingName);
        if (parking != null) {
            // Recherche du bloc
            Block block = blockrepository.findBlockByBlockName(Blockname);
            if (block != null) {
                // Recherche de la place dans le bloc
                Places place = placerepository.findPlacebyidentf(PlaceName);
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
    
    
       
}