export interface IBirim {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    carpani?: number;
    aktifMi?: boolean;
}

export class Birim implements IBirim {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public carpani?: number,
        public aktifMi?: boolean
    ) {
        this.aktifMi = this.aktifMi || false;
    }
}
