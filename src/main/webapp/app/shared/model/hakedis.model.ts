import { Moment } from 'moment';
import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { ISantral } from 'app/shared/model/santral.model';
import { IEkip } from 'app/shared/model/ekip.model';
import { IProje } from 'app/shared/model/proje.model';

export const enum IsDurumu {
    BEKLIYOR = 'BEKLIYOR',
    BEKLIYOR_MALZEME = 'BEKLIYOR_MALZEME',
    BEKLIYOR_RUHSAT = 'BEKLIYOR_RUHSAT',
    CALISILIYOR = 'CALISILIYOR',
    TAMAMLANDI = 'TAMAMLANDI',
    TAMAMLANDI_EKSIK_VAR = 'TAMAMLANDI_EKSIK_VAR'
}

export const enum OdemeDurumu {
    BEKLIYOR = 'BEKLIYOR',
    HAZIR = 'HAZIR',
    HAKEDISI_YAPILDI = 'HAKEDISI_YAPILDI',
    ODEME_BEKLIYOR = 'ODEME_BEKLIYOR',
    ODEME_ALINDI = 'ODEME_ALINDI',
    TAMAMLANDI = 'TAMAMLANDI'
}

export interface IHakedis {
    id?: number;
    ad?: string;
    aciklama?: string;
    detay?: any;
    durumu?: IsDurumu;
    seriNo?: number;
    odemeDurumu?: OdemeDurumu;
    odemeNo?: string;
    resimContentType?: string;
    resim?: any;
    tarih?: Moment;
    dosyaContentType?: string;
    dosya?: any;
    hakedisDetays?: IHakedisDetay[];
    santral?: ISantral;
    ekip?: IEkip;
    proje?: IProje;
}

export class Hakedis implements IHakedis {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public detay?: any,
        public durumu?: IsDurumu,
        public seriNo?: number,
        public odemeDurumu?: OdemeDurumu,
        public odemeNo?: string,
        public resimContentType?: string,
        public resim?: any,
        public tarih?: Moment,
        public dosyaContentType?: string,
        public dosya?: any,
        public hakedisDetays?: IHakedisDetay[],
        public santral?: ISantral,
        public ekip?: IEkip,
        public proje?: IProje
    ) {}
}
