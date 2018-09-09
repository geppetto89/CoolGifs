package michele.meninno.coolgifs.repository;

import java.util.ArrayList;
import java.util.List;

import michele.meninno.coolgifs.core.Mapper;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.network.dto.GifResponse;
import michele.meninno.coolgifs.network.dto.SingleGifImages;
import michele.meninno.coolgifs.network.dto.TrendingResponse;

public class NewTrendingGIfsMapper implements Mapper<TrendingResponse, List<GifModel>> {
    @Override
    public List<GifModel> map(TrendingResponse elementToMap) {
        List<GifModel> gifModels = new ArrayList<>();
        for (GifResponse elem : elementToMap.getGifs()) {
            if (elem.getImages() != null && elem.getImages().getFixedHeightAnimated() != null && elem.getImages().getFixedHeightStill() != null) {
                SingleGifImages images = elem.getImages();
                gifModels.add(new GifModel(images.getFixedHeightStill().getUrl(), images.getFixedHeightAnimated().getUrl(), elem.getId()));
            }
        }
        return gifModels;
    }
}
