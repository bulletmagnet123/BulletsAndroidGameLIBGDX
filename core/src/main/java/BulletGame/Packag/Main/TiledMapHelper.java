package BulletGame.Packag.Main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapHelper {
    public TiledMap tiledMap;
    public TiledMapTileLayer collisionLayer;



    public TiledMapHelper(){
        setupMap();

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(tiledMap.getLayers().getIndex("layer1"));
    }

    public OrthogonalTiledMapRenderer setupMap(){
        tiledMap = new TmxMapLoader().load("map.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
    public int getTileWidth() {
        TiledMapTileLayer tileLayer = getCollisionLayer();
        return tileLayer != null ? tileLayer.getTileWidth() : 0;
    }

    public int getTileHeight() {
        TiledMapTileLayer tileLayer = getCollisionLayer();
        return tileLayer != null ? tileLayer.getTileHeight() : 0;
    }
    public TiledMap getTiledMap() {
        return tiledMap;
    }




}
