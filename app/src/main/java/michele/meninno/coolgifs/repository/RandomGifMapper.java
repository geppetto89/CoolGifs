package michele.meninno.coolgifs.repository;

import michele.meninno.coolgifs.core.Mapper;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.network.dto.RandomGifResponse;
import michele.meninno.coolgifs.network.dto.SingleGifImages;

public class RandomGifMapper implements Mapper<RandomGifResponse, GifModel>{

    @Override
    public GifModel map(RandomGifResponse response) {
        SingleGifImages images = response.getGif().getImages();
        GifModel gifModel = new GifModel(images.getFixedHeightStill().getUrl(), images.getFixedHeightAnimated().getUrl(), response.getGif().getId());
        return gifModel;
    }
}
