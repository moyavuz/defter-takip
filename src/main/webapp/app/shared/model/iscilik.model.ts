import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';
import { IEkip } from 'app/shared/model/ekip.model';
import { IProje } from 'app/shared/model/proje.model';

export const enum IscilikDurumu {
    BEKLIYOR = 'BEKLIYOR',
    BEKLIYOR_MALZEME = 'BEKLIYOR_MALZEME',
    BEKLIYOR_RUHSAT = 'BEKLIYOR_RUHSAT',
    CALISILIYOR = 'CALISILIYOR',
    TAMAMLANDI = 'TAMAMLANDI',
    TAMAMLANDI_EKSIK_VAR = 'TAMAMLANDI_EKSIK_VAR'
}

export interface IIscilik {
    id?: number;
    ad?: string;
    aciklama?: string;
    detay?: any;
    durumu?: IscilikDurumu;
    resimContentType?: string;
    resim?: any;
    detays?: IIscilikDetay[];
    iscilikEkip?: IEkip;
    iscilik?: IProje;
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public detay?: any,
        public durumu?: IscilikDurumu,
        public resimContentType?: string,
        public resim?: any,
        public detays?: IIscilikDetay[],
        public iscilikEkip?: IEkip,
        public iscilik?: IProje
    ) {}
}