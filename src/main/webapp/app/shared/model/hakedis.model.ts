import { Moment } from 'moment';
import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { ISantral } from 'app/shared/model/santral.model';
import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';
import { IEkip } from 'app/shared/model/ekip.model';
import { IProje } from 'app/shared/model/proje.model';

export const enum OnemDurumu {
    ACIL = 'ACIL',
    IVEDI = 'IVEDI',
    NORMAL = 'NORMAL'
}

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
    tarih?: Moment;
    seriNo?: number;
    defterNo?: string;
    cizimNo?: number;
    onemDerecesi?: OnemDurumu;
    isDurumu?: IsDurumu;
    odemeDurumu?: OdemeDurumu;
    odemeNo?: string;
    aciklama?: string;
    detay?: any;
    resimContentType?: string;
    resim?: any;
    dosyaContentType?: string;
    dosya?: any;
    hakedisDetays?: IHakedisDetay[];
    santral?: ISantral;
    turu?: IHakedisTuru;
    ekip?: IEkip;
    proje?: IProje;
}

export class Hakedis implements IHakedis {
    constructor(
        public id?: number,
        public ad?: string,
        public tarih?: Moment,
        public seriNo?: number,
        public defterNo?: string,
        public cizimNo?: number,
        public onemDerecesi?: OnemDurumu,
        public isDurumu?: IsDurumu,
        public odemeDurumu?: OdemeDurumu,
        public odemeNo?: string,
        public aciklama?: string,
        public detay?: any,
        public resimContentType?: string,
        public resim?: any,
        public dosyaContentType?: string,
        public dosya?: any,
        public hakedisDetays?: IHakedisDetay[],
        public santral?: ISantral,
        public turu?: IHakedisTuru,
        public ekip?: IEkip,
        public proje?: IProje
    ) {}
}
